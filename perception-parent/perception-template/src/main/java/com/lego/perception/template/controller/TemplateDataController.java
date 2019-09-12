package com.lego.perception.template.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.excel.utils.ExcelTemplateUtil;
import com.framework.word.util.WordTemplateUtil;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.business.feign.BusinessClient;
import com.lego.framework.file.feign.FileClient;
import com.lego.framework.file.feign.model.UploadFile;
import com.lego.framework.system.feign.DataFileClient;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.template.service.IFormTemplateService;
import com.lego.perception.template.util.CSVUtils;
import com.lego.perception.template.util.JsonXmlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @auther xiaodao
 * @date 2019/9/3 18:20
 */
@RestController
@RequestMapping("/templateData/v1")
@Resource(value = "templateData", desc = "模板数据管理")
@Api(tags = "templateData", description = "模板数据管理")
@Slf4j
public class TemplateDataController {
    @Autowired
    private IFormTemplateService formTemplateService;
    @Autowired
    private BusinessClient businessClient;
    @Autowired
    private FileClient fileClient;

    @Autowired
    private DataFileClient dataFileClient;

    /**
     * 数据上传
     *
     * @param req
     * @param templateId
     * @return
     */
    @PostMapping("/upload")
    public void uploadData(HttpServletRequest req, Long templateId, Long projectId, Integer sourceType) {

        // 1 根据模板id查寻模板
        FormTemplate template = new FormTemplate();
        template.setId(templateId);
        template = formTemplateService.find(template);

        // 2. 解析数据组装成List<Map>结构
        List<Map<String, Object>> maps = new ArrayList<>();


        List<MultipartFile> fileList = new ArrayList<>();
        List<DataFile> dataFiles = new ArrayList<>();
        if (req instanceof MultipartHttpServletRequest) {
            fileList = ((MultipartHttpServletRequest) req).getFiles("file");
        }
        Long fileId = 1L;
        fileList.forEach(file -> {
            try {
                UploadFile uploadFile = new UploadFile();
                uploadFile.setExt(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".") + 1));
                uploadFile.setFileName(file.getOriginalFilename());
                uploadFile.setContent(file.getBytes());
                RespVO<Map<String, Object>> mapRespVO = fileClient.appUpload(uploadFile);
                if (mapRespVO.getRetCode() == 1) {
                    String url = mapRespVO.getInfo().get("data").toString();
                    DataFile dataFile = new DataFile();
                    dataFile.setProjectId(projectId);
                    dataFile.setDeleteFlag(1);
                    dataFile.setFileUrl(url);
                    dataFile.setFileType(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".") + 1));
                    dataFile.setName(file.getOriginalFilename());
                    dataFiles.add(dataFile);
                }

                maps.addAll(analyticalData(file, templateId, sourceType));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        if (CollectionUtils.isEmpty(dataFiles)) {
            ExceptionBuilder.operateFailException("上传数据失败");
        }
        RespVO<RespDataVO<Long>> respDataVORespVO = dataFileClient.insertList(dataFiles);

        // 3 调用接口上传数据
        businessClient.insertBusinessData(template, respDataVORespVO.getInfo().getList().get(0), maps, sourceType);
    }

    /**
     * 数据下载
     *
     * @param templateId   模板id
     * @param map          查詢条件
     * @param documentType 下载文件类型 0 xslx ,1 csv, 2 xml ,3 json
     * @return
     */
    @PostMapping("/download")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateId", value = "模板ID", dataType = "long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "documentType", value = "下载文件格式", dataType = "int", required = true, paramType = "query"),
    })
    public void downloadData(HttpServletResponse response, Long templateId, Map<String, Object> map, Integer sourceType, int documentType) throws IOException, SAXException {

        // 1 根据模板id查寻模板
        FormTemplate template = new FormTemplate();
        template.setId(templateId);
        template = formTemplateService.find(template);
        RespVO<List<Map<String, Object>>> query = businessClient.queryBusinessData(template, map, sourceType);
        List<Map<String, Object>> dataList = query.getInfo();
        response.setContentType("application/force-download");

        if (documentType == 0) {
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(template.getTemplateName() + ".xslx", "UTF-8"));
            Map<String, Object> headerMap = new HashMap<>();

            if (!CollectionUtils.isEmpty(query.getInfo())) {
                headerMap = dataList.get(0);
                List<String> headers = new ArrayList<>(headerMap.keySet());
                XSSFWorkbook wb = new XSSFWorkbook();
                XSSFSheet sheet = wb.createSheet();
                XSSFRow row = sheet.createRow(0);

                for (int i = 0; i < headers.size(); i++) {

                    XSSFCell cell = row.createCell(i);
                    //设置单元格内容
                    cell.setCellValue(headers.get(i));
                }
                for (int i = 0; i < dataList.size(); i++) {
                    XSSFRow rowValue = sheet.createRow(i + 1);
                    for (int j = 0; j < headers.size(); j++) {
                        XSSFCell cell = rowValue.createCell(i);
                        //设置单元格内容
                        cell.setCellValue(dataList.get(j).get(headers.get(j)).toString());
                    }

                }
                OutputStream output = response.getOutputStream();

                wb.write(output);
                output.close();
            }
        } else if (documentType == 1) {
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(template.getTemplateName() + ".csv", "UTF-8"));
            Map<String, Object> headerMap = new HashMap<>();

            if (!CollectionUtils.isEmpty(query.getInfo())) {
                headerMap = dataList.get(0);
                List<String> headers = new ArrayList<>(headerMap.keySet());
                List<String> values = new ArrayList<>();
                StringBuilder headerBuilder = new StringBuilder();
                for (int i = 0; i < headers.size(); i++) {
                    headerBuilder.append(headers.get(i));
                    if (i != headers.size() - 1) {
                        headerBuilder.append(",");
                    }
                }
                values.add(headerBuilder.toString());
                for (int i = 0; i < dataList.size(); i++) {
                    StringBuilder valueBuilder = new StringBuilder();
                    for (int j = 0; j < headers.size(); j++) {
                        valueBuilder.append(dataList.get(i).get(headers.get(j)));
                        if (j != headers.size() - 1) {
                            valueBuilder.append(",");
                        }
                    }
                    values.add(valueBuilder.toString());
                }
                OutputStream output = response.getOutputStream();
                output.close();
            }

        } else if (documentType == 2) {
            response.addHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(template.getTemplateName() + ".xml", "UTF-8"));
            if (dataList.size() == 1) {
                String formatXml;
                if (dataList.size() == 1) {
                    com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject(dataList.get(0));
                    formatXml = JsonXmlUtils.JsonToXml(jsonObject);
                    OutputStream output = response.getOutputStream();
                    output.write(formatXml.getBytes());
                    output.close();
                } else {
                    ExceptionBuilder.operateFailException("数据为多个，不能导出为xml格式文件");
                }
            }
        } else if (documentType == 3) {
            response.addHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(template.getTemplateName() + ".json", "UTF-8"));
            String formatJson;
            if (dataList.size() == 1) {
                formatJson = JsonXmlUtils.formatJson(dataList.get(0).toString());
            } else {
                formatJson = JsonXmlUtils.formatJson(dataList.toString());
            }
            OutputStream output = response.getOutputStream();
            output.write(formatJson.getBytes());
            output.close();
        }


    }

    public List<Map<String, Object>> analyticalData(MultipartFile file, Long templateId, Integer sourceType) throws IOException, JSONException {
        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            ExceptionBuilder.operateFailException("文件名异常");
        }
        if (file.getOriginalFilename().endsWith(".xlsx")) {
            try {
                XSSFWorkbook xsf = new XSSFWorkbook(file.getInputStream());
                ExcelTemplateUtil excelTemplateUtil = new ExcelTemplateUtil();
                List<Map<String, Object>> sheetValue = excelTemplateUtil.getSheetValue(xsf.getSheetAt(0), null);
                return sheetValue;
            } catch (IOException e) {
                ExceptionBuilder.operateFailException("文件异常");
            }


        } else if (file.getOriginalFilename().endsWith(".docx")) {
            WordTemplateUtil wordTemplateUtil = new WordTemplateUtil();
            XWPFDocument doc = new XWPFDocument(file.getInputStream());
            List<Map<String, Object>> excelData = wordTemplateUtil.getExcelData(doc, 0, null);
            return excelData;

        } else if (file.getOriginalFilename().endsWith(".xml")) {
            String str = IOUtils.toString(file.getInputStream(), "utf-8");
            JSONObject xmlJSONObj = XML.toJSONObject(str);
            Map<String,Object> map = com.alibaba.fastjson.JSONObject.parseObject(xmlJSONObj.toString(), Map.class);
            List<Map<String, Object>> dataList = new ArrayList<>();
            dataList.add(map);
            return dataList;

        } else if (file.getOriginalFilename().endsWith(".json")) {
            String str = IOUtils.toString(file.getInputStream(), "utf-8");
            Map<String,Object> map = com.alibaba.fastjson.JSONObject.parseObject(str, Map.class);
            List<Map<String, Object>> resultList = new ArrayList<>();
            resultList.add(map);

        } else if (file.getOriginalFilename().endsWith(".csv")) {
            List<String> dataList = CSVUtils.importCsv(file.getInputStream());
            List<Map<String, Object>> resultList = new ArrayList<>();
            if (dataList != null && !dataList.isEmpty()) {
                String header = dataList.get(0);
                String[] headers = header.split(",");
                for (int i = 0; i < dataList.size(); i++) {
                    //不读取第一行
                    if (i != 0) {
                        String s = dataList.get(i);
                        String[] as = s.split(",");
                        for (int j = 0; j < as.length; j++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put(headers[j], as[j]);
                            resultList.add(map);
                        }

                    }
                }
            }
            return resultList;
        }


        return null;

    }


}
