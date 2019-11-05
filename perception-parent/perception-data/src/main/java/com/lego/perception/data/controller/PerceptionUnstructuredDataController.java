package com.lego.perception.data.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.base.utils.ZipUtil;
import com.lego.framework.data.model.entity.PerceptionStructuredData;
import com.lego.framework.data.model.entity.PerceptionUnstructuredData;
import com.lego.framework.file.feign.PerceptionFileClient;
import com.lego.framework.file.model.PerceptionFile;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.data.service.IPerceptionUnstructuredDataService;
import com.lego.perception.data.utils.TemplateDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.*;


/**
 * PerceptionUnstructuredData
 *
 * @author ¸ßÀÚ
 * @email 513684652@qq.com
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/perceptionUnstructuredData")
@Api(value = "PerceptionUnstructuredData管理", tags = "PerceptionUnstructuredData管理")
@Validated
@Slf4j
public class PerceptionUnstructuredDataController {
    @Autowired
    private IPerceptionUnstructuredDataService perceptionUnstructuredDataService;
    @Autowired
    private PerceptionFileClient perceptionFileClient;

    @ApiOperation(value = "分页查询PerceptionUnstructuredData", notes = "分页查询PerceptionUnstructuredData")
    @ApiImplicitParams({
    })
    @Operation(value = "select_paged", desc = "查询维修费用")
    @GetMapping(value = "/select_paged/{pageSize}/{pageIndex}")
    public RespVO<PagedResult<PerceptionUnstructuredData>> selectPaged(PerceptionUnstructuredData perceptionUnstructuredData,
                                                                       @PathParam(value = "") Page page) {
        PagedResult<PerceptionUnstructuredData> pageResult = perceptionUnstructuredDataService.selectPaged(perceptionUnstructuredData, page);
        return RespVOBuilder.success(pageResult);
    }


    @ApiOperation(value = "通过主键id查询PerceptionUnstructuredData", notes = "通过主键id查询PerceptionUnstructuredData")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "id", required = true, dataType = "Long")
    })
    @GetMapping("/{id}")
    public RespVO<PerceptionUnstructuredData> selectByPrimaryKey(@PathVariable(value = "id") Long id) {
        PerceptionUnstructuredData perceptionUnstructuredData =
                perceptionUnstructuredDataService.selectByPrimaryKey(id);
        if (perceptionUnstructuredData == null) {
            return RespVOBuilder.failure("当前PerceptionUnstructuredData不存在");
        } else {
            return RespVOBuilder.success(perceptionUnstructuredData);
        }
    }

    @ApiOperation(value = "通过主键id删除PerceptionUnstructuredData", notes = "通过主键id删除PerceptionUnstructuredData")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "id", required = true, dataType = "Long")
    })
    @DeleteMapping("/{id}")
    public RespVO deleteByPrimaryKey(@PathVariable(value = "id") Long id) {
        Integer num = perceptionUnstructuredDataService.deleteByPrimaryKey(id);
        if (num == 0) {
            return RespVOBuilder.failure("删除PerceptionUnstructuredData失败");
        } else {
            return RespVOBuilder.success("删除PerceptionUnstructuredData成功");
        }
    }

    @ApiOperation(value = "新增PerceptionUnstructuredData", notes = "新增PerceptionUnstructuredData")
    @ApiImplicitParams({
    })
    @PostMapping("/")
    public RespVO insert(@RequestBody PerceptionUnstructuredData perceptionUnstructuredData) {
        if (perceptionUnstructuredData == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = perceptionUnstructuredDataService.insert(perceptionUnstructuredData);
        if (num == 0) {
            return RespVOBuilder.failure("添加PerceptionUnstructuredData失败");
        } else {
            return RespVOBuilder.success("添加PerceptionUnstructuredData成功");
        }
    }

    @ApiOperation(value = "修改PerceptionUnstructuredData", notes = "修改PerceptionUnstructuredData")
    @ApiImplicitParams({
    })
    @PutMapping("/")
    public RespVO updateByPrimaryKey(@RequestBody PerceptionUnstructuredData perceptionUnstructuredData) {
        if (perceptionUnstructuredData == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = perceptionUnstructuredDataService.updateByPrimaryKey(perceptionUnstructuredData);
        if (num == 0) {
            return RespVOBuilder.failure("修改PerceptionUnstructuredData失败");
        } else {
            return RespVOBuilder.success("修改PerceptionUnstructuredData成功");
        }
    }


    @ApiOperation(value = "通过主键id批量删除PerceptionUnstructuredData", notes = "通过主键id批量删除PerceptionUnstructuredData")
    @ApiImplicitParams({
    })
    @DeleteMapping("/deleteBatchPrimaryKeys")
    public RespVO deleteBatchPrimaryKeys(@RequestBody List<Long> list) {
        if (CollectionUtils.isEmpty(list)) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = perceptionUnstructuredDataService.deleteBatchIds(list);
        if (num == 0) {
            return RespVOBuilder.failure("批量删除PerceptionUnstructuredData失败");
        } else {
            return RespVOBuilder.success("批量删除PerceptionUnstructuredData成功");
        }
    }


    @ApiOperation(value = "条件查询PerceptionUnstructuredData", notes = "条件查询PerceptionUnstructuredData")
    @ApiImplicitParams({
    })
    @PostMapping("/list")
    public RespVO query(@RequestBody PerceptionUnstructuredData perceptionUnstructuredData) {
        if (perceptionUnstructuredData == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        List<PerceptionUnstructuredData> list = perceptionUnstructuredDataService.query(perceptionUnstructuredData);
        return RespVOBuilder.success(list);
    }


    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    @Operation(value = "upload", desc = "格式化文件上传")
    public RespVO upload(@RequestParam(value = "files", required = true) MultipartFile[] files,
                         @RequestParam(value = "businessModule", required = false) String businessModule,
                         @RequestParam(value = "sourceModule", required = false) String sourceModule,
                         @RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "projectId", required = false) Long projectId,
                         @RequestParam(value = "remark", required = false) String remark,
                         @RequestParam(value = "tags", required = false) String tags,
                         @RequestParam(value = "createBy", required = false) String createBy
    ) {
        if (files == null || files.length <= 0) {
            return RespVOBuilder.failure("上传文件为空");
        }


        RespVO<RespDataVO<PerceptionFile>> respDataVORespVO = perceptionFileClient.upload(files, businessModule, projectId, remark, tags, createBy, 0);
        if (respDataVORespVO.getRetCode() != 1) {
            return RespVOBuilder.failure("上传文件失败");
        }
        List<PerceptionFile> perceptionFileList = respDataVORespVO.getInfo().getList();
        if (CollectionUtils.isEmpty(perceptionFileList)){
            return RespVOBuilder.failure("上传文件失败");
        }


        PerceptionUnstructuredData perceptionUnstructuredData = new PerceptionUnstructuredData();
        perceptionUnstructuredData.setPublishFlag(0);
        perceptionUnstructuredData.setDeleteFlag(0);
        perceptionUnstructuredData.setBatchNum(perceptionFileList.get(0).getBatchNum());
        perceptionUnstructuredData.setBusinessModule(businessModule);
        perceptionUnstructuredData.setCreatedBy(createBy);
        perceptionUnstructuredData.setCreationDate(new Date());
        perceptionUnstructuredData.setLastUpdateDate(new Date());
        perceptionUnstructuredData.setLastUpdatedBy(createBy);
        perceptionUnstructuredData.setTags(tags);
        perceptionUnstructuredData.setRemark(remark);
        perceptionUnstructuredData.setName(name);
        perceptionUnstructuredData.setSourceModule(sourceModule);
        perceptionUnstructuredData.setProjectId(projectId);

        Long size = 0L;
        for (MultipartFile f: files){
            size = size +f.getSize();
        }
        perceptionUnstructuredData.setSize(size);
        perceptionUnstructuredDataService.insert(perceptionUnstructuredData);
        return RespVOBuilder.success(perceptionUnstructuredData);
    }


    @GetMapping("/download")
    public void download(HttpServletResponse response, @RequestParam(value = "batchnum") String batchnum) {
        PerceptionFile perceptionFile = new PerceptionFile();
        PerceptionUnstructuredData perceptionStructuredData = new PerceptionUnstructuredData();
        perceptionStructuredData.setBatchNum(batchnum);
        List<PerceptionUnstructuredData> perceptionUnstructuredDataList = perceptionUnstructuredDataService.query(perceptionStructuredData);
        if (CollectionUtils.isEmpty(perceptionUnstructuredDataList)){
            ExceptionBuilder.operateFailException("该批次号文件不存在");
        }
        perceptionFile.setBatchNum(batchnum);
        perceptionFile.setDeleteFlag(0);
        perceptionFile.setIsStructured(1);
        RespVO<RespDataVO<PerceptionFile>> query = perceptionFileClient.query(perceptionFile);

        if (query.getRetCode() == 1) {
            List<PerceptionFile> perceptionFiles = query.getInfo().getList();
            if (CollectionUtils.isEmpty(perceptionFiles)){
                ExceptionBuilder.operateFailException("文件不存在");
            }
            Map<String, byte[]> map = new HashMap<>();
            perceptionFiles.stream().forEach(pf -> {
                map.put(pf.getName(), ZipUtil.getFileByte(pf.getFileUrl()));
            });
            String zipName = perceptionUnstructuredDataList.get(0).getName()== null?batchnum:perceptionUnstructuredDataList.get(0).getName();

            ZipUtil.downloadBatchByFile(response, map, zipName);
        } else {
            ExceptionBuilder.operateFailException("文件下载失败");
        }

    }

}
