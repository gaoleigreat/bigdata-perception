package com.lego.perception.data.controller;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.file.feign.FileClient;
import com.lego.framework.system.model.entity.DataFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @auther xiaodao
 * @date 2019/9/17 16:53
 */
@RestController
@RequestMapping("/local/data")
@Api(value = "data", description = "内部数据上传")
@Resource(value = "data", desc = "内部数据上传")
@Slf4j
public class LocalDataController {
    @Autowired
    private FileClient fileClient;


    @ApiOperation(value = "formatted", notes = "格式化文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "格式化文件上传，", paramType = "formData", allowMultiple = true, required = true, dataType = "file"),
            @ApiImplicitParam(name = "projectId", value = "工程Id，", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "remark", value = "说明，", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "tags", value = "标签，", paramType = "query", required = false, dataType = "String"),
    })
    @PostMapping(value = "/upload/formatted", headers = "content-type=multipart/form-data")
    @Operation(value = "formatted", desc = "格式化文件上传")
    public RespVO uplodeFormatted(
            @RequestParam(value = "files", required = true) MultipartFile[] files,
            @RequestParam(value = "projectId", required = false) Long projectId,
            @RequestParam(value = "remark", required = false) String remark,
            @RequestParam(value = "tags", required = false) String tags) {
        if (files == null || files.length <= 0) {
            return RespVOBuilder.failure("上传文件为空");
        }

        RespVO<RespDataVO<DataFile>> uploads = fileClient.uploads(files, projectId, null, -1, remark, tags);
        if (uploads.getRetCode() != 1) {
            return RespVOBuilder.failure("上传文件失败");
        }
        if (uploads.getInfo().getList().size() <= 0) {
            return RespVOBuilder.failure("上传文件失败");
        }
        return RespVOBuilder.success(uploads.getInfo().getList().get(0).getBatchNum());


    }


    @ApiOperation(value = "非格式化文件上传", notes = "非格式化文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "格式化文件上传，", paramType = "formData", allowMultiple = true, required = true, dataType = "file"),
            @ApiImplicitParam(name = "projectId", value = "工程Id，", paramType = "query", required = false, dataType = "long"),
            @ApiImplicitParam(name = "remark", value = "说明", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "tags", value = "标签", paramType = "query", required = false, dataType = "String", example = "文件,设备,建筑"),
    })
    @PostMapping(value = "/upload/unformatted", headers = "content-type=multipart/form-data")
    @Operation(value = "unformatted", desc = "非格式化文件上传")
    public RespVO uplodeFormatted(@RequestParam(value = "projectId", required = false) Long projectId,
                                  @RequestParam(value = "files", required = true) MultipartFile[] files,
                                  @RequestParam(value = "remark", required = false) String remark,
                                  @RequestParam(value = "tags", required = false) String tags) {
        Set<Character> set = new HashSet<>();

        char[] chars1 = remark.toCharArray();
        for (char c1 : chars1) {
            set.add(c1);
        }
        if (files == null || files.length <= 0) {
            return RespVOBuilder.failure("上传文件有误");
        }
        RespVO<RespDataVO<DataFile>> uploads = fileClient.uploads(files, projectId, null, -1, remark, tags);
        if (uploads.getRetCode() != 1) {
            return RespVOBuilder.failure("上传文件失败");
        }
        if (uploads.getInfo().getList().size() <= 0) {
            return RespVOBuilder.failure("上传文件失败");
        }
        return RespVOBuilder.success(uploads.getInfo().getList().get(0).getBatchNum());

    }


}
