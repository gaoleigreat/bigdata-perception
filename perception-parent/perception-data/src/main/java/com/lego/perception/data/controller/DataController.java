package com.lego.perception.data.controller;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.utils.UuidUtils;
import com.lego.framework.file.feign.FileClient;
import com.lego.framework.file.model.UploadFile;
import com.lego.framework.system.feign.DataFileClient;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.data.service.IBusinessService;
import com.lego.perception.data.utils.TemplateDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @auther xiaodao
 * @date 2019/9/17 16:53
 */
@RestController
@RequestMapping("/data")
@Api(value = "data", description = "数据上传")
@Resource(value = "data", desc = "数据上传")
@Slf4j
public class DataController {
    @Autowired
    private FileClient fileClient;

    @Autowired
    private DataFileClient dataFileClient;
    @Autowired
    @Qualifier(value = "mySqlBusinessServiceImpl")
    private IBusinessService mySqlBusinessService;

    @Autowired
    @Qualifier(value = "mongoBusinessServiceImpl")
    private IBusinessService mongoBusinessService;

    @ApiOperation(value = "formatted", notes = "格式化文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "格式化文件上传，", paramType = "formData", allowMultiple = true, required = true, dataType = "file"),
            @ApiImplicitParam(name = "templateId", value = "模板Id，", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "projectId", value = "工程Id，", paramType = "query", required = false, dataType = "Long"),
    })
    @PostMapping(value = "/upload/formatted", headers = "content-type=multipart/form-data")
    @Operation(value = "formatted", desc = "格式化文件上传")
    public RespVO<RespDataVO<Long>> uplodeFormatted(HttpServletRequest request, @RequestParam(value = "templateId", required = true) Long templateId, @RequestParam(value = "projectId", required = false) String projectId, @RequestParam(value = "files", required = true) MultipartFile[] files) {
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
        List<Long> fileIds = new ArrayList<>();
        Arrays.stream(files).forEach(mf -> {

            log.error(mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".") + 1));
            UploadFile uploadFile = new UploadFile();
            try {
                uploadFile.setContent(mf.getBytes());
                uploadFile.setExt(mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".") + 1));
                String fileName = UuidUtils.generateShortUuid();
                String fileType = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".") + 1);
                uploadFile.setFileName(UuidUtils.generateShortUuid());
                // RespVO<Map<String, Object>> mapRespVO = fileClient.appUpload(uploadFile);
                //String url = mapRespVO.getInfo().get("url").toString();
                String url = new String();
                DataFile dataFile = new DataFile();
                dataFile.setName(fileName);
                dataFile.setFileType(fileType);
                dataFile.setFileUrl(url);
                dataFile.setPreviewUrl(url);
                dataFile.setCreateInfo();
                RespVO<Long> insert = dataFileClient.insert(dataFile);
                String sourceType = new String();
                if (insert.getInfo() != null) {
                    List<Map<String, Object>> maps = TemplateDataUtil.analyticalData(mf, insert.getInfo());
                    if (sourceType.equals("mysql")) {
                        mySqlBusinessService.insertBusinessData(template, maps, insert.getInfo());
                    } else {
                        mongoBusinessService.insertBusinessData(template, maps, insert.getInfo());
                    }
                    fileIds.add(insert.getInfo());
                } else {
                    fileIds.add(1L);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        });


        return RespVOBuilder.success(fileIds);
    }


    @ApiOperation(value = "非格式化文件上传", notes = "非格式化文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "非格式化文件上传，", paramType = "formData", allowMultiple = true, required = true, dataType = "file"),
            @ApiImplicitParam(name = "projectId", value = "工程Id，", paramType = "query", required = false, dataType = "Long"),
    })
    @PostMapping(value = "/upload/unformatted", headers = "content-type=multipart/form-data")
    @Operation(value = "unformatted", desc = "非格式化文件上传")
    public RespVO<RespDataVO<Long>> uplodeFormatted(@RequestParam(value = "projectId", required = false) String projectId, @RequestParam(value = "files", required = true) MultipartFile[] files) {
        if (files == null || files.length <= 0) {
            return RespVOBuilder.failure("上传文件有误");
        }
        List<Long> fileIds = new ArrayList<>();
        Arrays.stream(files).forEach(f -> {
            //进行文件上传
            fileIds.add(f.getSize());
        });
        return RespVOBuilder.success(fileIds);

    }
}
