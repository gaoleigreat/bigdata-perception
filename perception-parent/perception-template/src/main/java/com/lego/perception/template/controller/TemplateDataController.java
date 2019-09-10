package com.lego.perception.template.controller;

import com.framework.common.sdto.RespVO;
import com.framework.excel.utils.ExcelTemplateUtil;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.business.feign.BusinessClient;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.template.service.IFormTemplateService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private BusinessClient businessClient;

    /**
     * 数据上传
     *
     * @param req
     * @param templateId
     * @return
     */
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
            maps.addAll(analyticalData(file, templateId, sourceType));
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

    public List<Map<String, Object>> analyticalData(MultipartFile file, Long templateId, Integer sourceType) {
        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            ExceptionBuilder.operateFailException("文件名异常");
        }
        if (file.getOriginalFilename().endsWith(".xlsx")) {
            try {
                XSSFWorkbook xsf = new XSSFWorkbook(file.getInputStream());
                ExcelTemplateUtil excelTemplateUtil = new ExcelTemplateUtil();
                // List<Map<String, Object>> sheetValue = excelTemplateUtil.getSheetValue(xsf.getSheetAt(0), null);
                return null;
            } catch (IOException e) {
                ExceptionBuilder.operateFailException("文件异常");
            }


        } else if (file.getOriginalFilename().endsWith(".docx")) {

        }


        return null;

    }

}
