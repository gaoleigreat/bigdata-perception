package com.lego.perception.file.controller;

import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.utils.UuidUtils;
import com.lego.framework.equipment.model.entity.EquipmentCost;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.perception.file.model.UploadFile;
import com.lego.perception.file.service.IDataFileService;
import com.lego.perception.file.service.IFdfsFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.framework.common.page.Page;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.*;

@RestController
@Api(value = "FileController", description = "规则和不规则文件上传")
@RequestMapping("/datefile/v1")
@Slf4j
public class DataFileController {

    @Autowired
    private IDataFileService dataFileService;

    @ApiOperation(value = "多文件上传", notes = "多文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "多个文件，", paramType = "formData", allowMultiple = true, required = true, dataType = "file"),
            @ApiImplicitParam(name = "projectId", value = "项目id，", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "templateId", value = "模板id，", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "sourceType", value = "数据类型，", required = false, dataType = "int"),
            @ApiImplicitParam(name = "remark", value = "备注", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "tags", value = "标签", paramType = "query", required = false, dataType = "String"),

    })
    @PostMapping(value = "/uploads", headers = "content-type=multipart/form-data")
    public RespVO uploads(@RequestParam(value = "files", required = true) MultipartFile[] files,
                          @RequestParam(value = "projectId", required = false) Long projectId,
                          @RequestParam(value = "templateId", required = false) Long templateId,
                          @RequestParam(value = "sourceType", required = false) int sourceType,
                          @RequestParam(value = "remark", required = false) String remark,
                          @RequestParam(value = "tags", required = false) String tags

    ) {
        String batchNum = UuidUtils.generate16Uuid();

        List<DataFile> returnList = new ArrayList<>();
        List<DataFile> dataFiles = new ArrayList<>();
        Map<String, String> upload = dataFileService.uploadToHdfs(null, null, files);
        for (MultipartFile f : files) {
            String filename = f.getOriginalFilename();
            if (upload != null && upload.containsKey(filename)) {
                Map<String, Object> fileMap = new HashMap<>(2);
                fileMap.put("fileName", filename);
                fileMap.put("url", upload.get(filename));
                int pos = f.getOriginalFilename().lastIndexOf(".");
                String ext = null;
                if (pos > -1 && pos + 1 < filename.length()) {
                    ext = filename.substring(pos + 1);
                }
                DataFile dataFile = new DataFile(filename, ext, projectId, upload.get(filename), upload.get(filename), templateId, 0, sourceType, 0, remark, tags, batchNum);
                dataFile.setDataSize(f.getSize());
                dataFiles.add(dataFile);
                returnList.add(dataFile);
            }
        }
        dataFileService.batchInsert(dataFiles);
        return RespVOBuilder.success(returnList);
    }


    /**
     * 分页查询数据
     */
    @ApiOperation(value = "查询维修费用", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "查询维修费用")
    @RequestMapping(value = "/select_paged/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public RespVO<PagedResult<DataFile>> selectPaged(@ModelAttribute DataFile dataFile,
                                                     @PathParam(value = "") Page page) {
        PagedResult<DataFile> dataFilePagedResult = dataFileService.selectPaged(dataFile, page);
        return RespVOBuilder.success(dataFilePagedResult);
    }

    @ApiOperation(value = "通过主键id查询DataFile", notes = "通过主键id查询DataFile")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "id", required = true, dataType = "Long")
    })
    @GetMapping("/{id}")
    public RespVO<DataFile> selectByPrimaryKey(@PathVariable(value = "id") Long id) {
        DataFile dataFile =
                dataFileService.selectByPrimaryKey(id);
        if (dataFile == null) {
            return RespVOBuilder.failure("当前DataFile不存在");
        } else {
            return RespVOBuilder.success(dataFile);
        }
    }

    @ApiOperation(value = "通过主键id删除DataFile", notes = "通过主键id删除DataFile")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "id", required = true, dataType = "Long")
    })
    @DeleteMapping("/{id}")
    public RespVO deleteByPrimaryKey(@PathVariable(value = "id") Long id) {
        Integer num = dataFileService.deleteByPrimaryKey(id);
        if (num == 0) {
            return RespVOBuilder.failure("删除DataFile失败");
        } else {
            return RespVOBuilder.success("删除DataFile成功");
        }
    }

    @ApiOperation(value = "新增DataFile", notes = "新增DataFile")
    @ApiImplicitParams({
    })
    @PostMapping("/")
    public RespVO insert(@RequestBody DataFile dataFile) {
        if (dataFile == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = dataFileService.insert(dataFile);
        if (num == 0) {
            return RespVOBuilder.failure("添加DataFile失败");
        } else {
            return RespVOBuilder.success("添加DataFile成功");
        }
    }

    @ApiOperation(value = "修改DataFile", notes = "修改DataFile")
    @ApiImplicitParams({
    })
    @PutMapping("/")
    public RespVO updateByPrimaryKey(@RequestBody DataFile dataFile) {
        if (dataFile == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = dataFileService.updateByPrimaryKey(dataFile);
        if (num == 0) {
            return RespVOBuilder.failure("修改DataFile失败");
        } else {
            return RespVOBuilder.success("修改DataFile成功");
        }
    }


    @ApiOperation(value = "通过主键id批量删除DataFile", notes = "通过主键id批量删除DataFile")
    @ApiImplicitParams({
    })
    @DeleteMapping("/deleteBatchPrimaryKeys")
    public RespVO deleteBatchPrimaryKeys(@RequestBody List<Long> list) {
        if (CollectionUtils.isEmpty(list)) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = dataFileService.deleteBatchIds(list);
        if (num == 0) {
            return RespVOBuilder.failure("批量删除DataFile失败");
        } else {
            return RespVOBuilder.success("批量删除DataFile成功");
        }
    }


    @ApiOperation(value = "条件查询DataFile", notes = "条件查询DataFile")
    @ApiImplicitParams({
    })
    @PostMapping("/list")
    public RespVO query(@RequestBody DataFile dataFile) {
        if (dataFile == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        List<DataFile> list = dataFileService.query(dataFile);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "条件查询DataFile", notes = "条件查询DataFile")
    @ApiImplicitParams({

    })
    @GetMapping("/queryPaged/{pageSize}/{pageIndex}")
    public RespVO<PagedResult<DataFile>> queryByListBatch(@ModelAttribute DataFile dataFile,
                                                          @PathParam(value = "") Page page) {
        if (dataFile == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        PagedResult<DataFile> listBatch = dataFileService.queryByListBatch(dataFile, page);
        return RespVOBuilder.success(listBatch);
    }


    @ApiOperation(value = "通过批次号查询", notes = "通过批次号查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "batchNums", value = "批次号，", paramType = "query", allowMultiple = true, required = true, dataType = "String"),
            @ApiImplicitParam(name = "tags", value = "标签(多标签用逗号隔开)", paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/selectByBatchNums", method = RequestMethod.GET)
    @Operation(value = "select", desc = "通过批次号查询")
    public RespVO selectByBatchNums(@RequestParam(value = "bathNums") List<String> batchNums,
                                    @RequestParam(required = false) String tags) {
        return dataFileService.selectBybatchNums(batchNums, tags);
    }



    @ApiOperation(value = "通过批次号查询", notes = "通过批次号查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "batchNums", value = "批次号，", paramType = "query", allowMultiple = true, required = true, dataType = "String"),
            @ApiImplicitParam(name = "tags", value = "标签(多标签用逗号隔开)", paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/updateCheckStatusByBatchNums", method = RequestMethod.POST)
    public RespVO updateCheckStatusByBatchNums(@RequestParam(value = "bathNums") List<String> batchNums,
                                        @RequestParam(required = false, value = "tags") String tags){
        return dataFileService.updateCheckStatusByBatchNums(batchNums, tags);
    }



    @ApiOperation(value = "上传业务文件", notes = "上传业务文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "多个文件，", paramType = "formData", allowMultiple = true, required = true, dataType = "file"),
            @ApiImplicitParam(name = "tags", value = "标签(多标签用逗号隔开)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", paramType = "query", dataType = "String")
    })
    @PostMapping(value = "/upLoadFile", headers = "content-type=multipart/form-data")
    @Operation(value = "upLoadFile", desc = "上传业务文件")
    public RespVO<RespDataVO<DataFile>> upLoadFile(@RequestParam(value = "files", required = true) MultipartFile[] files,
                                                   @RequestParam(required = false) String remark,
                                                   @RequestParam(required = false) String tags) {
        return dataFileService.upLoadFile(files, remark, tags);
    }


    @ApiOperation(value = "testUpLoad", notes = "testUpLoad")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "多个文件，", paramType = "formData", allowMultiple = true, required = true, dataType = "file")
    })
    @PostMapping(value = "/testUpLoad")
    @Operation(value = "testUpLoad", desc = "test多文件上传")
    public RespVO testUpLoad(@RequestParam(value = "files", required = true) MultipartFile[] files) {
        return RespVOBuilder.success(files.length);
    }

    @ApiOperation(value = "testOneUpLoad", notes = "testOneUpLoad")
    @ApiImplicitParams({})
    @PostMapping(value = "/testOneUpLoad", headers = "content-type=multipart/form-data")
    @Operation(value = "testOneUpLoad", desc = "testOneUpLoad")
    public RespVO testOneUpLoad(@RequestParam(value = "file", required = true) MultipartFile file) {
        return RespVOBuilder.success(file.getSize());
    }
}
