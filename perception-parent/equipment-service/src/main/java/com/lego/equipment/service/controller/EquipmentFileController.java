package com.lego.equipment.service.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.IEquipmentFileService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.equipment.model.entity.EquipmentFile;
import com.lego.framework.file.feign.FileClient;
import com.lego.framework.system.model.entity.DataFile;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-08 08:42:13
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/equipmentfile")
public class EquipmentFileController {
    @Autowired
    private IEquipmentFileService equipmentFileService;

    @Autowired
    private FileClient fileClient;

    /**
     * 分页查询数据
     */
    @RequestMapping("/select_paged")
    public RespVO<PagedResult<EquipmentFile>> selectPaged(EquipmentFile equipmentFile, Page page) {

        PagedResult<EquipmentFile> pageResult = equipmentFileService.selectPaged(equipmentFile, page);
        return RespVOBuilder.success(pageResult);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @RequestMapping("/select_by_id")
    public RespVO<EquipmentFile> selectByPrimaryKey(Long fileId) {
        EquipmentFile po = equipmentFileService.selectByPrimaryKey(fileId);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @RequestMapping("/delete_by_id")
    public RespVO deleteByPrimaryKey(Long fileId) {
        Integer num = equipmentFileService.deleteByPrimaryKey(fileId);
        if (num != null && num > 0) {
            return RespVOBuilder.success();
        }else {
            return RespVOBuilder.failure("删除失败");
        }
    }

    /**
     * 新增数据
     *
     * @return
     */
    @RequestMapping("/save_equipmentFile")
    public RespVO insert(EquipmentFile equipmentFile) {
        Integer num = equipmentFileService.insertSelective(equipmentFile);
        if (num != null && num > 0) {
            return RespVOBuilder.success();
        }else {
            return RespVOBuilder.failure("插入失败");
        }
    }

    /**
     * 修改数据
     *
     * @return
     */
    @RequestMapping("/update_equipmentFile")
    public RespVO updateByPrimaryKeySelective(EquipmentFile equipmentFile) {
        Integer num = equipmentFileService.updateByPrimaryKeySelective(equipmentFile);
        if (num != null && num > 0) {
            return RespVOBuilder.success();
        }else {
            return RespVOBuilder.failure("更新失败");
        }
    }


    /**
     * 查询列表
     *
     * @return
     */
    @RequestMapping("/query_list")
    public RespVO<RespDataVO<EquipmentFile>> queryByCondition(EquipmentFile equipmentFile) {

        List<EquipmentFile> list = equipmentFileService.query(equipmentFile);
        if (CollectionUtils.isEmpty(list)){
            List<EquipmentFile> emptyResult = new ArrayList<>();
            return RespVOBuilder.success(emptyResult);
        }
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "formatted", notes = "格式化文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "格式化文件上传，", paramType = "formData", allowMultiple = true, required = true, dataType = "file"),
            @ApiImplicitParam(name = "equipmentId", value = "模板Id，", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "equipmentCode", value = "工程Id，", paramType = "query", required = false, dataType = "Long"),
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
