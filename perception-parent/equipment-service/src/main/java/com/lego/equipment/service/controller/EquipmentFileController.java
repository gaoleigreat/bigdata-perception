package com.lego.equipment.service.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.IEquipmentFileService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.context.RequestContext;
import com.lego.framework.equipment.feign.EquipmentMaintenanceDocClient;
import com.lego.framework.equipment.model.entity.EquipmentFile;
import com.lego.framework.file.feign.HDFSFileClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-08 08:42:13
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/equipmentfile")
@Api(value = "设备文档管理", description = "设备文档管理")
public class EquipmentFileController {
    @Autowired
    private IEquipmentFileService equipmentFileService;

    @Autowired
    private HDFSFileClient hdfsFileClient;

    @Autowired
    private EquipmentMaintenanceDocClient equipmentMaintenanceDocClient;


    /**
     * 分页查询数据
     */
    @ApiOperation(value = "分页查询数据", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "分页查询数据")
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
    @ApiOperation(value = "查询设备文档", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "查询设备文档", dataType = "Long", required = true, paramType = "query"),
    })
    @Operation(value = "select_by_id", desc = "查询设备文档")
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
    @ApiOperation(value = "删除设备文档", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "删除设备文档", dataType = "Long", required = true, paramType = "query"),
    })
    @Operation(value = "delete_by_id", desc = "删除设备文档")
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
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
    @ApiOperation(value = "新增数据", httpMethod = "POST")
    @ApiImplicitParams({

    })
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
    @ApiOperation(value = "修改数据", httpMethod = "POST")
    @ApiImplicitParams({

    })
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
    @ApiOperation(value = "查询文件", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @RequestMapping("/query_list")
    public RespVO<RespDataVO<EquipmentFile>> queryByCondition(EquipmentFile equipmentFile) {

        List<EquipmentFile> list = equipmentFileService.query(equipmentFile);
        if (CollectionUtils.isEmpty(list)){
            List<EquipmentFile> emptyResult = new ArrayList<>();
            return RespVOBuilder.success(emptyResult);
        }
        return RespVOBuilder.success(list);
    }






    @ApiOperation(value = "设备相关文件上传", notes = "设备相关文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "格式化文件上传，", paramType = "formData", allowMultiple = true, required = true, dataType = "file"),
            @ApiImplicitParam(name = "equipmentId", value = "工程Id，", paramType = "query", required = false, dataType = "long"),
            @ApiImplicitParam(name = "equipmentCode", value = "设备编号", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "说明", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "tags", value = "标签", paramType = "query", required = false, dataType = "String", example = "文件,设备,建筑"),
    })
    @PostMapping(value = "/upload/unformatted", headers = "content-type=multipart/form-data")
    public RespVO uplodeUnFormatted(@RequestParam(value = "files", required = true) MultipartFile[] files,
                                  @RequestParam(value = "equipmentId", required = true) Long equipmentId,
                                  @RequestParam(value = "equipmentCode", required = false) String equipmentCode,
                                  @RequestParam(value = "remark", required = false) String remark,
                                  @RequestParam(value = "tags", required = false) String tags) {

        if (files ==null || files.length==0){
            return RespVOBuilder.failure("上传文件不能为空");
        }
        RespVO<Map<String, String>> respVO = hdfsFileClient.uploads("", "", files);
        if (respVO ==null || respVO.getRetCode() != 1){
            return RespVOBuilder.failure("文件上传失败");
        }
        List<EquipmentFile> equipmentFiles = new ArrayList<>();
        Map<String, String> respVOInfo = respVO.getInfo();
        Arrays.stream(files).forEach(f ->{
            EquipmentFile equipmentFile = new EquipmentFile();
            equipmentFile.setDeleteFlag(0);
            equipmentFile.setEquipmentCode(equipmentCode);
            equipmentFile.setEquipmentId(equipmentId);
            equipmentFile.setFileName(f.getOriginalFilename());
            equipmentFile.setFileUrl(respVOInfo.get(f.getOriginalFilename()));
            equipmentFile.setCreateInfo();
            equipmentFile.setCreatedBy(RequestContext.getCurrent().getUserId());
            equipmentFileService.insert(equipmentFile);
            equipmentFiles.add(equipmentFile);
            equipmentMaintenanceDocClient.upload(f,equipmentFile.getFileId());
        });

      return RespVOBuilder.success(equipmentFiles);

    }

}
