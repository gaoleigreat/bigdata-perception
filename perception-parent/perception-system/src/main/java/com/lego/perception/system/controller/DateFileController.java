package com.lego.perception.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.file.feign.FileClient;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.framework.system.model.entity.Dictionary;
import com.lego.perception.system.service.IDataFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

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
    @ApiOperation(value = "根据编码查询code", httpMethod = "GET")
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
    public RespVO insertList(@RequestBody List<DataFile> dataFiles) {

        return dataFileService.insertList(dataFiles);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(value = "insert", desc = "新增")
    @ApiOperation("新增")
    public RespVO insert(@RequestBody DataFile dataFile) {

        return dataFileService.insert(dataFile);
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
