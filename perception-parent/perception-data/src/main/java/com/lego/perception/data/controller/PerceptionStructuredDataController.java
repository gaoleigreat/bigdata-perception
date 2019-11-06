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
import com.lego.framework.template.feign.TemplateFeignClient;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.data.service.IDataService;
import com.lego.perception.data.service.IPerceptionStructuredDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.*;



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
    private TemplateFeignClient templateFeignClient;
    @Autowired
    private IPerceptionStructuredDataService perceptionStructuredDataService;

    @Autowired
    @Qualifier(value = "mySqlDataServiceImpl")
    private IDataService mySqlDataService;

    @Autowired
    @Qualifier(value = "mongoDataServiceImpl")
    private IDataService mongoDataService;

    @Autowired
    private PerceptionFileClient perceptionFileClient;

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
        if (perceptionStructuredData == null) {
            return RespVOBuilder.failure("当前PerceptionStructuredData不存在");
        } else {
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
        if (perceptionStructuredData == null) {
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
        if (perceptionStructuredData == null) {
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
    public RespVO<RespDataVO<PerceptionStructuredData>> query(@RequestBody PerceptionStructuredData perceptionStructuredData) {
        if (perceptionStructuredData == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        List<PerceptionStructuredData> list = perceptionStructuredDataService.query(perceptionStructuredData);
        return RespVOBuilder.success(list);
    }
    @ApiOperation(value = "上传", notes = "上传")
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    @Operation(value = "upload", desc = "格式化文件上传")
    public RespVO upload(@RequestParam(value = "templateId", required = true) Long templateId,
                         @RequestParam(value = "files", required = true) MultipartFile[] files,
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
        if (templateId == null) {
            return RespVOBuilder.failure("模板Id不能为空");
        }

        // 1 根据模板id查寻模板
        RespVO<FormTemplate> formTemplateById = templateFeignClient.findFormTemplateById(templateId);
        FormTemplate template = formTemplateById.getInfo();
        if (template == null) {
            return RespVOBuilder.failure("所选模板不存在");
        }
        RespVO<RespDataVO<PerceptionFile>> respDataVORespVO = perceptionFileClient.upload(files, businessModule, projectId, remark, tags, createBy, 0);
        if (respDataVORespVO.getRetCode() != 1) {
            return RespVOBuilder.failure("上传文件失败");
        }
        List<PerceptionFile> perceptionFileList = respDataVORespVO.getInfo().getList();
        if (CollectionUtils.isEmpty(perceptionFileList)) {
            return RespVOBuilder.failure("上传文件失败");
        }

        PerceptionStructuredData perceptionStructuredData = new PerceptionStructuredData();
        perceptionStructuredData.setPublishFlag(0);
        perceptionStructuredData.setDeleteFlag(0);
        perceptionStructuredData.setBatchNum(perceptionFileList.get(0).getBatchNum());
        perceptionStructuredData.setBusinessModule(businessModule);
        perceptionStructuredData.setCreatedBy(createBy);
        perceptionStructuredData.setCreationDate(new Date());
        perceptionStructuredData.setLastUpdateDate(new Date());
        perceptionStructuredData.setLastUpdatedBy(createBy);
        perceptionStructuredData.setTemplateId(templateId);
        perceptionStructuredData.setTags(tags);
        perceptionStructuredData.setRemark(remark);
        perceptionStructuredData.setName(name);
        perceptionStructuredData.setSourceModule(sourceModule);
        perceptionStructuredData.setProjectId(projectId);

        Long size = 0L;
        for (MultipartFile f : files) {
            size = size + f.getSize();
        }
        perceptionStructuredData.setSize(size);
        Integer insert = perceptionStructuredDataService.insert(perceptionStructuredData);
        if(insert>0) {
            return RespVOBuilder.success(perceptionStructuredData);
        }
        return RespVOBuilder.failure("上次失败");
    }


    @ApiOperation(value = "download", notes = "download")
    @ApiImplicitParams({
    })
    @GetMapping("/download")
    public void download(HttpServletResponse response, @RequestParam(value = "batchnum") String batchnum) {
        PerceptionFile perceptionFile = new PerceptionFile();
        PerceptionStructuredData perceptionStructuredData = new PerceptionStructuredData();
        perceptionStructuredData.setBatchNum(batchnum);
        List<PerceptionStructuredData> perceptionStructuredDataList = perceptionStructuredDataService.query(perceptionStructuredData);
        if (CollectionUtils.isEmpty(perceptionStructuredDataList)) {
            ExceptionBuilder.operateFailException("该批次号文件不存在");
        }
        perceptionFile.setBatchNum(batchnum);
        perceptionFile.setDeleteFlag(0);
        perceptionFile.setIsStructured(0);
        RespVO<RespDataVO<PerceptionFile>> query = perceptionFileClient.query(perceptionFile);

        if (query.getRetCode() == 1) {
            List<PerceptionFile> perceptionFiles = query.getInfo().getList();
            if (CollectionUtils.isEmpty(perceptionFiles)) {
                ExceptionBuilder.operateFailException("文件不存在");
            }
            Map<String, byte[]> map = new HashMap<>();
            perceptionFiles.stream().forEach(pf -> {
                map.put(pf.getName(), ZipUtil.getFileByte(pf.getFileUrl()));
            });
            String zipName = perceptionStructuredDataList.get(0).getName() == null ? batchnum : perceptionStructuredDataList.get(0).getName();

            ZipUtil.downloadBatchByFile(response, map, zipName);
            ZipUtil.downloadBatchByFile(response, map, perceptionStructuredDataList.get(0).getName());
        } else {
            ExceptionBuilder.operateFailException("文件下载失败");
        }

    }

}
