package com.lego.perception.file.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.utils.UuidUtils;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.perception.file.model.UploadFile;
import com.lego.perception.file.service.IDataFileService;
import com.lego.perception.file.service.IFdfsFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@Api(value = "FileController", description = "规则和不规则文件上传")
@RequestMapping("/datefile/v1")
@Slf4j
public class DataFileController {

    @Autowired
    private IFdfsFileService fdfsFileService;

    @Autowired
    private IDataFileService dataFileService;

    @ApiOperation(value = "多文件上传", notes = "多文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "多个文件，", paramType = "formData", allowMultiple = true, required = true, dataType = "file"),
            @ApiImplicitParam(name = "projectId", value = "projectId，", paramType = "query", allowMultiple = true, required = true, dataType = "Long"),
            @ApiImplicitParam(name = "templateId", value = "templateId，", paramType = "query", allowMultiple = false, required = false, dataType = "Long"),
            @ApiImplicitParam(name = "sourceType", value = "sourceType，", paramType = "query", allowMultiple = false, required = true, dataType = "int"),
            @ApiImplicitParam(name = "remark", value = "remark，", paramType = "query", allowMultiple = true, required = false, dataType = "String"),
            @ApiImplicitParam(name = "tags", value = "tags，", paramType = "query", allowMultiple = true, required = false, dataType = "String"),
            @ApiImplicitParam(name = "equipmentId", value = "equipmentId，", paramType = "query", allowMultiple = true, required = false, dataType = "Long"),
            @ApiImplicitParam(name = "tags", value = "equipmentCode，", paramType = "equipmentCode", allowMultiple = true, required = false, dataType = "String")

    })
    @PostMapping(value = "/uploads", headers = "content-type=multipart/form-data")
    public RespVO uploads(@RequestParam(value = "files", required = true) MultipartFile[] files,
                                                @RequestParam(value = "projectId", required = true) Long projectId,
                                                @RequestParam(value = "templateId", required = false) Long templateId,
                                                @RequestParam(value = "sourceType", required = true) int sourceType,
                                                @RequestParam(value = "remark", required = false) String remark,
                                                @RequestParam(value = "tags", required = false) String tags,
                                                @RequestParam(value = "equipmentId", required = false) Long equipmentId,
                                                @RequestParam(value = "equipmentCode", required = false) String equipmentCode

    ) {
        String batchNum = UuidUtils.generate16Uuid();

        List<DataFile> returnList = new ArrayList<>();
        List<DataFile> dataFiles = new ArrayList<>();
        Arrays.stream(files).forEach(f -> {
            try {
                UploadFile uploadFile = new UploadFile();
                uploadFile.setFileName(f.getOriginalFilename());
                uploadFile.setContent(f.getBytes());
                if (!StringUtils.isEmpty(f.getOriginalFilename())) {
                    int pos = f.getOriginalFilename().lastIndexOf(".");

                    if (pos > -1 && pos + 1 < f.getOriginalFilename().length()) {
                        uploadFile.setExt(f.getOriginalFilename().substring(pos + 1));
                    }
                }
                RespVO<Map<String, Object>> upload = fdfsFileService.upload(uploadFile);
                if (1 == upload.getRetCode()) {
                    Map<String, Object> fileMap = new HashMap<>(2);
                    fileMap.put("fileName", f.getOriginalFilename());
                    fileMap.put("url", upload.getInfo().get("data"));
                    DataFile dataFile = new DataFile(uploadFile.getFileName(), uploadFile.getExt(), projectId, upload.getInfo().get("data").toString(), upload.getInfo().get("data").toString(), templateId, 0, sourceType, 0, remark, tags, batchNum);
                    if (null == equipmentId){
                        dataFile.setEquipmentId(equipmentId);
                       if (!StringUtils.isEmpty(equipmentCode)) {
                           dataFile.setEquipmentCode(equipmentCode);
                       }
                    }
                    dataFiles.add(dataFile);
                    returnList.add(dataFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        dataFileService.insertList(dataFiles);
        return RespVOBuilder.success(returnList);
    }


    @GetMapping(value = "/getDataFileById")
    @ApiOperation(value = "根据Id查询详情", httpMethod = "GET")
    @ApiImplicitParams(
            {
            }
    )
    public RespVO<DataFile> getDataFileById(@RequestParam(required = false) Long id) {
        DataFile dataFile = dataFileService.findById(id);
        if (dataFile != null) {
            return RespVOBuilder.success(dataFile);
        }
        return RespVOBuilder.failure("文件不存在");
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    public RespVO<RespDataVO<DataFile>> findList(@ModelAttribute DataFile dataFile) {
        List<DataFile> dataFiles = dataFileService.findList(dataFile);
        if (CollectionUtils.isNotEmpty(dataFiles)) {
            return RespVOBuilder.success(dataFiles);
        } else {
            return RespVOBuilder.failure();
        }

    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "pageIndex，", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize，", paramType = "query", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/findPagedList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    public RespVO<IPage<DataFile>> findPagedList(@ModelAttribute DataFile dataFile, @RequestParam(value = "pageIndex") int pageIndex,
                                                 @RequestParam(required = false, defaultValue = "10") int pageSize) {
        Page<DataFile> page = new Page<>(pageIndex, pageSize);
        return RespVOBuilder.success(dataFileService.findPagedList(dataFile, page));
    }

    @ApiOperation(value = "批量新增", notes = "批量新增")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/insertList", method = RequestMethod.POST)
    @Operation(value = "insert", desc = "批量新增")
    public RespVO<RespDataVO<Long>> insertList(@RequestBody List<DataFile> dataFiles) {

        return dataFileService.insertList(dataFiles);
    }

    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/updateList", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    public RespVO updateList(@RequestBody List<DataFile> dataFiles) {

        return dataFileService.updateList(dataFiles);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id，", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @Operation(value = "delete", desc = "删除")
    public RespVO deleteList(@RequestParam Long id) {

        return dataFileService.delete(id);
    }

    @ApiOperation(value = "批量删除", notes = "批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    @Operation(value = "delete", desc = "批量删除")
    public RespVO deleteList(@RequestBody List<Long> ids) {
        return dataFileService.deleteList(ids);
    }

    @ApiOperation(value = "通过批次号查询", notes = "通过批次号查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "batchNums", value = "批次号，", paramType = "query", allowMultiple = true, required = true, dataType = "String")
    })
    @RequestMapping(value = "/selectByBatchNums", method = RequestMethod.GET)
    @Operation(value = "select", desc = "通过批次号查询")
    public RespVO selectByBatchNums(@RequestParam(value = "bathNums") List<String> batchNums) {
        return dataFileService.selectBybatchNums(batchNums);
    }

}
