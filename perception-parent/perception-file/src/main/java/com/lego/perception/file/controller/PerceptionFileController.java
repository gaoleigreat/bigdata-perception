package com.lego.perception.file.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.base.utils.UuidUtils;
import com.lego.framework.file.model.PerceptionFile;
import com.lego.perception.file.service.IFdfsFileService;
import com.lego.perception.file.service.IPerceptionFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.*;


/**
 * PerceptionFile
 *
 * @author 高磊
 * @email 513684652@qq.com
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/perceptionFile")
@Api(value = "文件管理", tags = "文件管理")
@Validated
@Slf4j
public class PerceptionFileController {
    @Autowired
    private IPerceptionFileService perceptionFileService;

    @Autowired
    private IFdfsFileService fdfsFileService;


    @ApiOperation(value = "分页查询PerceptionFile", notes = "分页查询PerceptionFile")
    @ApiImplicitParams({})
    @Operation(value = "select_paged", desc = "查询维修费用")
    @GetMapping(value = "/select_paged/{pageSize}/{pageIndex}")
    public RespVO<PagedResult<PerceptionFile>> selectPaged(PerceptionFile perceptionFile,
                                                           @PathParam(value = "") Page page) {
        PagedResult<PerceptionFile> pageResult = perceptionFileService.selectPaged(perceptionFile, page);
        return RespVOBuilder.success(pageResult);
    }

    @ApiOperation(value = "通过主键id查询PerceptionFile", notes = "通过主键id查询PerceptionFile")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "id", required = true, dataType = "Long")
    })
    @GetMapping("/{id}")
    public RespVO<PerceptionFile> selectByPrimaryKey(@PathVariable(value = "id") Long id) {
        PerceptionFile perceptionFile =
                perceptionFileService.selectByPrimaryKey(id);
        if (perceptionFile == null) {
            return RespVOBuilder.failure("当前PerceptionFile不存在");
        } else {
            return RespVOBuilder.success(perceptionFile);
        }
    }

    @ApiOperation(value = "通过主键id删除PerceptionFile", notes = "通过主键id删除PerceptionFile")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "id", required = true, dataType = "Long")
    })
    @DeleteMapping("/{id}")
    public RespVO deleteByPrimaryKey(@PathVariable(value = "id") Long id) {
        Integer num = perceptionFileService.deleteByPrimaryKey(id);
        if (num == 0) {
            return RespVOBuilder.failure("删除PerceptionFile失败");
        } else {
            return RespVOBuilder.success("删除PerceptionFile成功");
        }
    }

    @ApiOperation(value = "新增PerceptionFile", notes = "新增PerceptionFile")
    @ApiImplicitParams({
    })
    @PostMapping("/")
    public RespVO insert(@RequestBody PerceptionFile perceptionFile) {
        if (perceptionFile == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = perceptionFileService.insert(perceptionFile);
        if (num == 0) {
            return RespVOBuilder.failure("添加PerceptionFile失败");
        } else {
            return RespVOBuilder.success("添加PerceptionFile成功");
        }
    }

    @ApiOperation(value = "修改PerceptionFile", notes = "修改PerceptionFile")
    @ApiImplicitParams({
    })
    @PutMapping("/")
    public RespVO updateByPrimaryKey(@RequestBody PerceptionFile perceptionFile) {
        if (perceptionFile == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = perceptionFileService.updateByPrimaryKey(perceptionFile);
        if (num == 0) {
            return RespVOBuilder.failure("修改PerceptionFile失败");
        } else {
            return RespVOBuilder.success("修改PerceptionFile成功");
        }
    }


    @ApiOperation(value = "通过主键id批量删除PerceptionFile", notes = "通过主键id批量删除PerceptionFile")
    @ApiImplicitParams({
    })
    @DeleteMapping("/deleteBatchPrimaryKeys")
    public RespVO deleteBatchPrimaryKeys(@RequestBody List<Long> list) {
        if (CollectionUtils.isEmpty(list)) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = perceptionFileService.deleteBatchIds(list);
        if (num == 0) {
            return RespVOBuilder.failure("批量删除PerceptionFile失败");
        } else {
            return RespVOBuilder.success("批量删除PerceptionFile成功");
        }
    }


    @ApiOperation(value = "条件查询PerceptionFile", notes = "条件查询PerceptionFile")
    @ApiImplicitParams({
    })
    @PostMapping("/list")
    public RespVO query(@RequestBody PerceptionFile perceptionFile) {
        if (perceptionFile == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        List<PerceptionFile> list = perceptionFileService.query(perceptionFile);
        return RespVOBuilder.success(list);
    }

    /**
     * 文件上传，返回批次还号
     * @param files
     * @param businessModule
     * @param projectId
     * @param remark
     * @param tags
     * @param createBy
     * @return
     */
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessModule", value = "业务模块，", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "projectId，", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "remark", value = "备注", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "tags", value = "标签", paramType = "query", required = false,dataType = "String"),
            @ApiImplicitParam(name = "createBy", value = "上传人", dataType = "String"),

    })
    @PostMapping(value = "/upLoad", headers = "content-type=multipart/form-data")
    public RespVO upload(@RequestParam(value = "files") MultipartFile[] files,
                         @RequestParam(value = "businessModule") String businessModule,
                         @RequestParam(value = "projectId", required = false) Long projectId,
                         @RequestParam(value = "remark") String remark,
                         @RequestParam(value = "tags",required = false) String tags,
                         @RequestParam(value = "createBy") String createBy,
                         @RequestParam(value = "isStructured",required = false,defaultValue = "1") int isStructured
    ) {
        Map<String, String> fileNamemap = new HashMap<>();
        String batchnum = UuidUtils.generateShortUuid();
        if (files == null || files.length == 0) {
            ExceptionBuilder.operateFailException("上传文件files不能为空");
        }
        List<PerceptionFile> perceptionFiles = new ArrayList<>();
        for (MultipartFile f : files) {
            String name = f.getOriginalFilename();

            Map<String, Object> objectMap = fdfsFileService.webUpload(f);
            if (objectMap == null || !objectMap.containsKey("data")) {
                ExceptionBuilder.operateFailException("上传文件失败");
            }
            String url = objectMap.get("data").toString();
            String previewUrl = url;
            PerceptionFile perceptionFile = new PerceptionFile();
            perceptionFile.setName(name);
            perceptionFile.setBatchNum(batchnum);
            perceptionFile.setSize(f.getSize());
            perceptionFile.setBatchNum(batchnum);
            perceptionFile.setTags(tags);
            perceptionFile.setDeleteFlag(0);
            perceptionFile.setCreationDate(new Date());
            perceptionFile.setLastUpdateDate(new Date());
            perceptionFile.setLastUpdatedBy(createBy);
            perceptionFile.setFileUrl(url);
            perceptionFile.setPreviewUrl(previewUrl);
            perceptionFile.setBusinessModule(businessModule);
            perceptionFile.setRemark(remark);
            perceptionFile.setCreatedBy(createBy);
            perceptionFile.setIsStructured(isStructured);
            perceptionFile.setExtName(name.substring(name.lastIndexOf(".") + 1));

            if (projectId != null) {
                perceptionFile.setProjectId(projectId);
            }
            perceptionFiles.add(perceptionFile);

        }
        if (!CollectionUtils.isEmpty(perceptionFiles)) {
            perceptionFileService.batchInsert(perceptionFiles);
            return RespVOBuilder.success(batchnum);
        } else {
            return RespVOBuilder.failure("上传失败");
        }
    }

}
