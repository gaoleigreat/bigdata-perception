package com.lego.perception.data.controller;

import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.FormTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @auther xiaodao
 * @date 2019/9/17 16:53
 */
@RestController
@RequestMapping("/data")
@Api(value = "数据上传", tags = "数据上传")
@Slf4j
public class DataController {

    @ApiOperation(value = "格式化文件上传", notes = "格式化文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "格式化文件上传，", paramType = "formData", allowMultiple = true, required = true, dataType = "file"),
            @ApiImplicitParam(name = "templateId", value = "模板Id，", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "projectId", value = "工程Id，", paramType = "query", required = false, dataType = "Long"),
    })
    @PostMapping(value = "/upload/formatted", headers = "content-type=multipart/form-data")
    public RespVO uplodeFormatted(HttpServletRequest request, @RequestParam(value = "templateId", required = true) Long templateId, @RequestParam(value = "projectId", required = false) String projectId, @RequestParam(value = "files", required = true) MultipartFile[] files) {
        if (files == null || files.length <= 0) {
            return RespVOBuilder.failure("上传文件为空");
        }
        if (templateId == null) {
            return RespVOBuilder.failure("模板Id不能为空");
        }

        // 1 根据模板id查寻模板
        FormTemplate template = new FormTemplate();
        template.setId(templateId);
        //template = formTemplateService.find(template);
        if (template == null) {
            return RespVOBuilder.failure("所选模板不存在");
        }
        Arrays.stream(files).forEach(mf -> {

            log.error(mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".") + 1));

        });


        return RespVOBuilder.success();
    }
}
