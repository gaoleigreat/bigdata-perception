package com.lego.perception.template.controller;

import com.alibaba.fastjson.JSON;
import com.framework.common.sdto.RespVO;
import com.framework.excel.utils.ExcelTemplateUtil;
import com.framework.word.util.WordTemplateUtil;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.business.feign.BusinessClient;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.template.service.IFormTemplateService;
import com.lego.perception.template.util.CSVUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
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

    /**
     * 数据上传
     *
     * @param req
     * @param templateId
     * @return
     */
    @PostMapping("/upload")
    public RespVO uploadData(HttpServletRequest req, Long templateId, Integer sourceType) {

        // 1 根据模板id查寻模板
        FormTemplate template = new FormTemplate();
        template.setId(templateId);
        template = formTemplateService.find(template);

        // 2. 解析数据组装成List<Map>结构
        List<Map<String, Object>> maps = new ArrayList<>();


        List<MultipartFile> fileList = new ArrayList<>();
        if (req instanceof MultipartHttpServletRequest) {
            fileList = ((MultipartHttpServletRequest) req).getFiles("file");
        }
        fileList.forEach(file -> {
            try {
                maps.addAll(analyticalData(file, templateId, sourceType));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        // 3 调用接口上传
        return businessClient.insertBusinessData(template, maps, sourceType);
    }

    /**
     * 数据下载
     *
     * @param templateId 模板id
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return
     */
    public RespVO downloadData(Long templateId, Long startTime, Long endTime) {

        return null;
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
            Iterator keys = xmlJSONObj.keys();
            List<Map<String, Object>> dataList = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            keys.forEachRemaining(key -> {
                try {
                    map.put(key.toString(), xmlJSONObj.get(key.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            dataList.add(map);
            return dataList;

        } else if (file.getOriginalFilename().endsWith(".json")) {
            String str = IOUtils.toString(file.getInputStream(), "utf-8");
            com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) JSON.parse(str);
            Set<String> strings = jsonObject.keySet();
            List<Map<String, Object>> datalist = new ArrayList<>();

            strings.forEach(s -> {
                Map<String, Object> map = new HashMap<>();
                map.put(s, jsonObject.get(s));
                datalist.add(map);

            });
            return datalist;
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
