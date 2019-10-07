package com.lego.equipment.service.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.excel.ExcelService;
import com.lego.equipment.service.listener.DocExcelReadListener;
import com.lego.equipment.service.service.IEquipmentMaintenanceDocService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.equipment.model.entity.EquipmentMaintenanceDoc;
import com.lego.framework.equipment.model.vo.EquipmentMaintenanceDocVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.InputStream;
import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 09:29:13
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/equipmentMaintenanceDoc")
@Api(value = "equipmentMaintenanceDoc", description = "设备保养手册管理")
@Resource(value = "equipmentMaintenanceDoc", desc = "设备保养手册管理")
public class EquipmentMaintenanceDocController {

    @Autowired
    private IEquipmentMaintenanceDocService iEquipmentMaintenanceDocService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private DocExcelReadListener docExcelReadListener;

    /**
     * 分页查询数据
     */
    @ApiOperation(value = "查询保养手册项", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "查询保养手册项")
    @RequestMapping(value = "/select_paged/{pageSize}/{pageIndex}", method = RequestMethod.POST)
    public RespVO<PagedResult<EquipmentMaintenanceDoc>> selectPaged(@ModelAttribute EquipmentMaintenanceDoc equipmentMaintenanceDoc,
                                                                    @PathParam(value = "") Page page) {
        PagedResult<EquipmentMaintenanceDoc> selectPaged = iEquipmentMaintenanceDocService.selectPaged(equipmentMaintenanceDoc, page);
        return RespVOBuilder.success(selectPaged);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @ApiOperation(value = "查询保养手册项", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "保养手册项ID", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "select_by_id", desc = "查询保养手册项")
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    public RespVO<EquipmentMaintenanceDoc> selectByPrimaryKey(@RequestParam Long id) {
        EquipmentMaintenanceDoc po = iEquipmentMaintenanceDocService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @ApiOperation(value = "删除保养手册项", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "保养手册项ID", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "delete_by_id", desc = "删除保养手册项")
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO deleteByPrimaryKey(@RequestParam Long id) {
        Integer num = iEquipmentMaintenanceDocService.deleteByPrimaryKey(id);
        if (num > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }

    /**
     * 新增数据
     *
     * @return
     */
    @ApiOperation(value = "新增保养保养手册项", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "save_equipmentMaintenanceDoc", desc = "新增保养保养手册项")
    @RequestMapping(value = "/save_equipmentMaintenanceDoc", method = RequestMethod.POST)
    public RespVO insert(@RequestBody EquipmentMaintenanceDoc equipmentMaintenanceDoc) {
        Integer num = iEquipmentMaintenanceDocService.insertSelective(equipmentMaintenanceDoc);
        if (num > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }

    /**
     * 修改数据
     *
     * @return
     */
    @ApiOperation(value = "修改保养手册项", httpMethod = "PUT")
    @ApiImplicitParams({

    })
    @Operation(value = "update_equipmentMaintenanceDoc", desc = "修改保养手册项")
    @RequestMapping(value = "/update_equipmentMaintenanceDoc", method = RequestMethod.PUT)
    public RespVO updateByPrimaryKeySelective(EquipmentMaintenanceDoc equipmentMaintenanceDoc) {
        Integer num = iEquipmentMaintenanceDocService.updateByPrimaryKeySelective(equipmentMaintenanceDoc);
        if (num > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


    /**
     * 查询列表
     *
     * @return
     */
    @ApiOperation(value = "查询保养手册项", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "query_list", desc = "查询保养手册项")
    @RequestMapping(value = "/query_list", method = RequestMethod.POST)
    public RespVO<RespDataVO<EquipmentMaintenanceDoc>> queryByCondition(@RequestBody EquipmentMaintenanceDoc equipmentMaintenanceDoc) {
        List<EquipmentMaintenanceDoc> list = iEquipmentMaintenanceDocService.query(equipmentMaintenanceDoc);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "导入保养手册项", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "upload", desc = "导入保养手册项")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    public RespVO upload(@RequestParam MultipartFile multipartFile) {
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            if (!StringUtils.isEmpty(originalFilename)) {
                InputStream inputStream = multipartFile.getInputStream();
                excelService.readExcel(originalFilename, docExcelReadListener, inputStream, EquipmentMaintenanceDocVo.class, 0);
                return RespVOBuilder.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespVOBuilder.failure("上传失败");
    }

}
