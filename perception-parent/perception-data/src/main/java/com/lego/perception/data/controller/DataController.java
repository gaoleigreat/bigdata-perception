package com.lego.perception.data.controller;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.file.feign.FileClient;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.framework.template.feign.TemplateFeignClient;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

    private HashMap<Integer, String> tags = new HashMap<>();

    @Autowired
    private TemplateFeignClient templateFeignClient;

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
            @ApiImplicitParam(name = "remark", value = "说明，", paramType = "query", required = false, dataType = "String"),
    })
    @PostMapping(value = "/upload/formatted", headers = "content-type=multipart/form-data")
    @Operation(value = "formatted", desc = "格式化文件上传")
    public RespVO uplodeFormatted(@RequestParam(value = "templateId", required = true) Long templateId,
                                  @RequestParam(value = "files", required = true) MultipartFile[] files,
                                  @RequestParam(value = "projectId", required = false) Long projectId,
                                  @RequestParam(value = "remark", required = false) String remark
    ) {
        if (files == null || files.length <= 0) {
            return RespVOBuilder.failure("上传文件为空");
        }
        if (templateId == null) {
            return RespVOBuilder.failure("模板Id不能为空");
        }

        // 1 根据模板id查寻模板
        RespVO<FormTemplate> formTemplateById = templateFeignClient.findFormTemplateById(templateId);
        FormTemplate template = formTemplateById.getInfo();
        if (template == null) {
            return RespVOBuilder.failure("所选模板不存在");
        }
        Integer dataType = template.getDataType();
        RespVO<RespDataVO<DataFile>> uploads = fileClient.uploads(files, projectId, templateId, template.getDataType(), remark, tags.get(dataType));
        Set<DataFile> dataFileSet = new HashSet<>();
        Arrays.stream(files).forEach(mf -> {

            List<DataFile> dataFiles = uploads.getInfo().getList();

            dataFiles.forEach(dataFile -> {
                if (mf.getOriginalFilename().endsWith(dataFile.getName())) {
                    dataFileSet.add(dataFile);
                    try {
                        List<Map<String, Object>> maps = TemplateDataUtil.analyticalData(mf, dataFile.getId(), template);
                        if (template.getType().equals("0")) {
                            mySqlBusinessService.insertBusinessData(template, maps, dataFile.getId());
                        } else {
                            mongoBusinessService.insertBusinessData(template, maps, dataFile.getId());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        });
        if (dataFileSet.size() != files.length) {
            return RespVOBuilder.failure("上传文件失败");
        }
        return RespVOBuilder.success(uploads.getInfo().getList().get(0).getBatchNum());

    }


    @ApiOperation(value = "非格式化文件上传", notes = "非格式化文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "格式化文件上传，", paramType = "formData", allowMultiple = true, required = true, dataType = "file"),
            @ApiImplicitParam(name = "projectId", value = "工程Id，", paramType = "query", required = false, dataType = "long"),
            @ApiImplicitParam(name = "remark", value = "说明", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "dataType", value = "数据类型(1-地形地貌;2-水文环境;3-市政管线;4-勘察设计;5-工程施工;6-装备运行;7-运营维护;8-其他)", paramType = "query", dataType = "int"),
    })
    @PostMapping(value = "/upload/unformatted", headers = "content-type=multipart/form-data")
    @Operation(value = "unformatted", desc = "非格式化文件上传")
    public RespVO uplodeFormatted(@RequestParam(value = "projectId", required = false) Long projectId,
                                  @RequestParam(value = "files", required = true) MultipartFile[] files,
                                  @RequestParam(value = "remark", required = false) String remark,
                                  @RequestParam(value = "dataType", required = false) Integer dataType
    ) {
        if (files == null || files.length <= 0) {
            return RespVOBuilder.failure("上传文件有误");
        }
        RespVO<RespDataVO<DataFile>> uploads = fileClient.uploads(files, projectId, null, -1, remark, tags.get(dataType));
        if (uploads.getRetCode() != 1) {
            return RespVOBuilder.failure("上传文件失败");
        }
        if (uploads.getInfo().getList().size() <= 0) {
            return RespVOBuilder.failure("上传文件失败");
        }
        return RespVOBuilder.success(uploads.getInfo().getList().get(0).getBatchNum());

    }


    @PostConstruct
    public void initTags() {
        tags.put(1, "地形地貌");
        tags.put(2, "水文环境");
        tags.put(3, "市政管线");
        tags.put(4, "勘探设计");
        tags.put(5, "工程施工");
        tags.put(6, "装备运行");
        tags.put(7, "运营维护");
        tags.put(8, "其他");
    }

    @PreDestroy
    public void destroy() {
        tags.clear();
        tags = null;
    }

}
