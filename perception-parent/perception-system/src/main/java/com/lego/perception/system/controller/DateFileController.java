package com.lego.perception.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.framework.common.consts.HttpConsts;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.file.feign.FileClient;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.perception.system.service.IDataFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/dateFile/v1")
@Resource(value = "dataFile", desc = "文件管理")
@Api(value = "DictionaryController", description = "文件管理")
@Slf4j
public class DateFileController {

    @Autowired
    private IDataFileService dataFileService;

    @Autowired
    private FileClient fileClient;


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

    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation(value = "查询列表，详情", httpMethod = "GET")
    public RespVO<RespDataVO<DataFile>> findList(@ModelAttribute DataFile dataFile) {
        List<DataFile> dataFiles = dataFileService.findList(dataFile);
        if (CollectionUtils.isNotEmpty(dataFiles)) {
            return RespVOBuilder.success(dataFiles);
        } else {
            return RespVOBuilder.failure();
        }

    }


    @RequestMapping(value = "/findPagedList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("分页查询")
    public RespVO<IPage<DataFile>> findPagedList(@ModelAttribute DataFile dataFile, @RequestParam(value = "pageIndex") int pageIndex,
                                                 @RequestParam(required = false, defaultValue = "10") int pageSize) {
        Page<DataFile> page = new Page<>(pageIndex, pageSize);
        return RespVOBuilder.success(dataFileService.findPagedList(dataFile, page));
    }

    @RequestMapping(value = "/insertList", method = RequestMethod.POST)
    @Operation(value = "insert", desc = "新增")
    @ApiOperation("批量新增")
    public RespVO<RespDataVO<Long>> insertList(@RequestBody List<DataFile> dataFiles) {

        return dataFileService.insertList(dataFiles);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(value = "insert", desc = "新增")
    @ApiOperation("新增")
    public RespVO<Long> insert(@RequestBody DataFile dataFile) {
        return dataFileService.insert(dataFile);
       /* List<MultipartFile> fileList = new ArrayList<>();
        if (req instanceof MultipartHttpServletRequest) {
            fileList = ((MultipartHttpServletRequest) req).getFiles("file");
        }

        if (CollectionUtils.isEmpty(fileList)) {
            return RespVOBuilder.failure("文件上传失败");
        }
        RespVO<List<Map<String, Object>>> respVO = fileClient.webUpload(req);
        if (respVO.getRetCode() != 1) {
            return RespVOBuilder.failure("文件上传失败");
        }
        List<Map<String, Object>> resultList = respVO.getInfo();
        List<DataFile> dataFiles = new ArrayList<>();
        resultList.stream().forEach(result -> {
            DataFile dataFile = new DataFile();
            dataFile.setDeleteFlag(1);
            Object fileName = result.get("fileName");
            Object fileUrl = result.get("url");
            if (StringUtils.isNotBlank(fileName.toString())) {
                dataFile.setName(fileName.toString());
                String suffix = fileName.toString().substring(fileName.toString().lastIndexOf(".") + 1);
                dataFile.setFileType(suffix);
            }
            if (StringUtils.isNotBlank(fileUrl.toString())) {
                dataFile.setName(fileUrl.toString());
            }
            if (projectId != null) {
                dataFile.setProjectId(projectId);
            } else {
                dataFile.setProjectId(1L);
            }


            if (result.get("fileName").toString().endsWith("dwg") || result.get("fileName").toString().endsWith("dxf") || result.get("fileName").toString().endsWith("dwt")) {
                File file = new File(result.get("fileName").toString());
                try {
                    FileUtils.copyURLToFile(new URL(result.get("url").toString()), file);

                    //文件转化为pdf,

                    //pdf 文件上传

                    //获取上传结果，赋值给previewUrl

                    //Image objImage = Image.load(new FileInputStream(file));
                } catch (IOException e) {
                    ExceptionBuilder.operateFailException("url转换文件失败");
                }

            }
            dataFiles.add(dataFile);

        });*/
        //return dataFileService.insertList(dataFiles);
    }

    @RequestMapping(value = "/updateList", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("更新")
    public RespVO updateList(@RequestBody List<DataFile> dataFiles) {

        return dataFileService.updateList(dataFiles);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @Operation(value = "delete", desc = "删除")
    @ApiOperation("删除")
    public RespVO deleteList(@RequestParam Long id) {

        return dataFileService.delete(id);
    }


    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    @Operation(value = "delete", desc = "删除")
    public RespVO deleteList(@RequestBody List<Long> ids) {
        return dataFileService.deleteList(ids);
    }
}
