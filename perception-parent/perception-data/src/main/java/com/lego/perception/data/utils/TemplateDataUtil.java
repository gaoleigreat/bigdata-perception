package com.lego.perception.data.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.framework.excel.utils.ExcelTemplateUtil;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.template.model.entity.FormTemplate;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.XML;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther xiaodao
 * @date 2019/9/17 17:10
 */
public class TemplateDataUtil {
    /**
     * 对数据进行解析
     *
     * @param file
     * @return
     * @throws IOException
     * @throws
     */
    public static List<Map<String, Object>> analyticalData(MultipartFile file, Long fileId, FormTemplate formTemplate) throws IOException, JSONException {
        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            ExceptionBuilder.operateFailException("文件名异常");
        }
        List<Map<String, Object>> mapArrayList = new ArrayList<>();
        if (file.getOriginalFilename().endsWith(".xlsx")) {
            try {
                XSSFWorkbook xsf = new XSSFWorkbook(file.getInputStream());
                ExcelTemplateUtil excelTemplateUtil = new ExcelTemplateUtil();
                mapArrayList = excelTemplateUtil.getSheetValue(xsf.getSheetAt(0), null);
            } catch (IOException e) {
                ExceptionBuilder.operateFailException("文件异常");
            }


        } else if (file.getOriginalFilename().endsWith(".xml")) {
            String str = IOUtils.toString(file.getInputStream(), "utf-8");
            org.json.JSONObject jsonObject = XML.toJSONObject(str);
            Map<String, Object> map = jsonObject.toMap();
            mapArrayList.add(map);
        } else if (file.getOriginalFilename().endsWith(".json")) {
            String str = IOUtils.toString(file.getInputStream(), "utf-8");
            Map<String, Object> map = JSONObject.parseObject(str, Map.class);
            mapArrayList.add(map);

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
                        Map<String, Object> map = new HashMap<>();
                        for (int j = 0; j < as.length; j++) {
                            map.put(headers[j], as[j]);
                        }
                        mapArrayList.add(map);

                    }
                }
            }
        }
        List<JSONObject> jsonObjects = new ArrayList<>();

        mapArrayList.forEach(map -> {
            JSONObject jsonObject = new JSONObject(map);
            jsonObject = TemplateDataValidataUtil.transformFormDataItem(formTemplate, jsonObject);
            jsonObject.put("fileId", fileId);
            jsonObjects.add(jsonObject);
        });
        List<Map<String, Object>> mapArrayListResult = new ArrayList<>();
        jsonObjects.forEach(jsonObject -> {
             Map map = JSON.parseObject(jsonObject.toJSONString(), Map.class);
            mapArrayListResult.add(map);
        });
        return mapArrayListResult;

    }
}
