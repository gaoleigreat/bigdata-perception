package com.lego.perception.data.controller;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.data.model.entity.PerceptionStructuredData;
import com.lego.perception.data.service.IPerceptionStructuredDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;


/**
 * PerceptionStructuredData
 *
 * @author ¸ßÀÚ
 * @email 513684652@qq.com
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/perceptionStructuredData")
@Api(value = "PerceptionStructuredData管理", tags = "PerceptionStructuredData管理")
@Validated
@Slf4j
public class PerceptionStructuredDataController {
    @Autowired
    private IPerceptionStructuredDataService perceptionStructuredDataService;

    @ApiOperation(value = "分页查询PerceptionStructuredData", notes = "分页查询PerceptionStructuredData")
    @ApiImplicitParams({
    })
    @Operation(value = "select_paged", desc = "查询维修费用")
    @GetMapping(value = "/select_paged/{pageSize}/{pageIndex}")
    public RespVO<PagedResult<PerceptionStructuredData>> selectPaged(PerceptionStructuredData perceptionStructuredData,
                                                                     @PathParam(value = "") Page page) {
        PagedResult<PerceptionStructuredData> pageResult = perceptionStructuredDataService.selectPaged(perceptionStructuredData, page);
        return RespVOBuilder.success(pageResult);
    }


    @ApiOperation(value = "通过主键id查询PerceptionStructuredData", notes = "通过主键id查询PerceptionStructuredData")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "id", required = true, dataType = "Long")
    })
    @GetMapping("/{id}")
    public RespVO<PerceptionStructuredData> selectByPrimaryKey(@PathVariable(value = "id") Long id) {
        PerceptionStructuredData perceptionStructuredData =
            perceptionStructuredDataService.selectByPrimaryKey(id);
        if (perceptionStructuredData == null){
            return RespVOBuilder.failure("当前PerceptionStructuredData不存在");
        } else{
            return RespVOBuilder.success(perceptionStructuredData);
        }
    }

    @ApiOperation(value = "通过主键id删除PerceptionStructuredData", notes = "通过主键id删除PerceptionStructuredData")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "id", required = true, dataType = "Long")
    })
    @DeleteMapping("/{id}")
    public RespVO deleteByPrimaryKey(@PathVariable(value = "id") Long id) {
        Integer num = perceptionStructuredDataService.deleteByPrimaryKey(id);
        if (num == 0) {
            return RespVOBuilder.failure("删除PerceptionStructuredData失败");
        } else {
            return RespVOBuilder.success("删除PerceptionStructuredData成功");
        }
    }

    @ApiOperation(value = "新增PerceptionStructuredData", notes = "新增PerceptionStructuredData")
    @ApiImplicitParams({
    })
    @PostMapping("/")
    public RespVO insert(@RequestBody PerceptionStructuredData perceptionStructuredData) {
        if (perceptionStructuredData == null){
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = perceptionStructuredDataService.insert(perceptionStructuredData);
        if (num == 0) {
            return RespVOBuilder.failure("添加PerceptionStructuredData失败");
        } else {
            return RespVOBuilder.success("添加PerceptionStructuredData成功");
        }
    }

    @ApiOperation(value = "修改PerceptionStructuredData", notes = "修改PerceptionStructuredData")
    @ApiImplicitParams({
    })
    @PutMapping("/")
    public RespVO updateByPrimaryKey(@RequestBody PerceptionStructuredData perceptionStructuredData) {
        if (perceptionStructuredData == null){
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = perceptionStructuredDataService.updateByPrimaryKey(perceptionStructuredData);
        if (num == 0) {
            return RespVOBuilder.failure("修改PerceptionStructuredData失败");
        } else {
            return RespVOBuilder.success("修改PerceptionStructuredData成功");
        }
    }


    @ApiOperation(value = "通过主键id批量删除PerceptionStructuredData", notes = "通过主键id批量删除PerceptionStructuredData")
    @ApiImplicitParams({
    })
    @DeleteMapping("/deleteBatchPrimaryKeys")
    public RespVO deleteBatchPrimaryKeys(@RequestBody List<Long> list) {
        if (CollectionUtils.isEmpty(list)) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = perceptionStructuredDataService.deleteBatchIds(list);
        if (num == 0) {
            return RespVOBuilder.failure("批量删除PerceptionStructuredData失败");
        } else {
            return RespVOBuilder.success("批量删除PerceptionStructuredData成功");
        }
    }


    @ApiOperation(value = "条件查询PerceptionStructuredData", notes = "条件查询PerceptionStructuredData")
    @ApiImplicitParams({
    })
    @PostMapping("/list")
    public RespVO query(@RequestBody PerceptionStructuredData perceptionStructuredData) {
        if (perceptionStructuredData ==null){
            return RespVOBuilder.failure("参数不能为空");
        }
        List<PerceptionStructuredData> list = perceptionStructuredDataService.query(perceptionStructuredData);
        return RespVOBuilder.success(list);
    }

/*    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    @Operation(value = "upload", desc = "格式化文件上传")
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
        if (uploads.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return uploads;
        }
        RespDataVO<DataFile> dataVO = uploads.getInfo();
        if (dataVO == null || CollectionUtils.isEmpty(dataVO.getList())) {
            return uploads;
        }
        Set<DataFile> dataFileSet = new HashSet<>();
        Arrays.stream(files).forEach(mf -> {
            List<DataFile> dataFiles = uploads.getInfo().getList();
            dataFiles.forEach(dataFile -> {
                if (mf.getOriginalFilename().endsWith(dataFile.getName())) {
                    dataFileSet.add(dataFile);
                    try {
                        List<Map<String, Object>> maps = TemplateDataUtil.analyticalData(mf, dataFile.getId(), template);
                        Integer type = template.getType();
                        if (type != null && type == 0) {
                            mySqlDataService.insertData(template, maps, dataFile.getId());
                        } else {
                            mongoDataService.insertData(template, maps, dataFile.getId());
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

    }*/




}
