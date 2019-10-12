package com.lego.equipment.service.controller;

import com.framework.common.consts.RespConsts;
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
import com.lego.framework.base.context.RequestContext;
import com.lego.framework.equipment.model.entity.EquipmentMaintenanceDoc;
import com.lego.framework.equipment.model.vo.EquipmentMaintenanceDocVo;
import com.lego.framework.file.feign.FileClient;
import com.lego.framework.system.model.entity.DataFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private FileClient fileClient;

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
            @ApiImplicitParam(name = "id", value = "保养手册项ID", dataType = "long", required = true, paramType = "query"),
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
            @ApiImplicitParam(name = "id", value = "保养手册项ID", dataType = "long", required = true, paramType = "query"),
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
    @ApiOperation(value = "新增保养保养手册", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "save_equipmentMaintenanceDoc", desc = "新增保养保养手册项")
    @RequestMapping(value = "/save_equipmentMaintenanceDoc", method = RequestMethod.POST)
    public RespVO insert(@RequestParam MultipartFile[] files,
                         @RequestParam(required = false) String remark) {
        RespVO<RespDataVO<DataFile>> respVO = fileClient.upLoad(files, remark, "保养手册", null);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("文件上传失败");
        }
        RespDataVO<DataFile> dataVO = respVO.getInfo();
        if (dataVO == null || CollectionUtils.isEmpty(dataVO.getList())) {
            return RespVOBuilder.failure("文件上传失败");
        }
        List<DataFile> fileList = dataVO.getList();
        List<DataFile> dataFileList = fileList.stream().filter(f -> f.getDataType() == 1).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(dataFileList)) {
            return RespVOBuilder.failure("请上传对应的结构化文件");
        }
        DataFile dataFile = dataFileList.get(0);
        for (MultipartFile file : files) {
            if (dataFile.getName().equals(file.getOriginalFilename())) {
                return upload(file, dataFile.getId());
            }
        }
        return RespVOBuilder.failure("新增保养手册失败");
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


    private RespVO upload(@RequestParam MultipartFile multipartFile, @RequestParam(value = "fileId") Long fileId) {
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            if (!StringUtils.isEmpty(originalFilename)) {
                InputStream inputStream = multipartFile.getInputStream();
                docExcelReadListener.setCreateByAndFileId(fileId, RequestContext.getCurrent().getUserId());
                excelService.readExcel(originalFilename, docExcelReadListener, inputStream, EquipmentMaintenanceDocVo.class, 1);
                return RespVOBuilder.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespVOBuilder.failure("上传失败");
    }

}
