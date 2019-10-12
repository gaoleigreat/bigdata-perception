package com.lego.equipment.service.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.excel.ExcelService;
import com.lego.equipment.service.listener.FittingExcelReadListener;
import com.lego.equipment.service.service.IFittingService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.equipment.model.entity.Fitting;
import com.lego.framework.equipment.model.vo.EquipmentMaintenanceDocVo;
import com.lego.framework.equipment.model.vo.FittingVo;
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
 * @date 2019-10-08 06:16:21
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/fitting")
@Api(value = "fitting", description = "配件管理")
@Resource(value = "fitting", desc = "配件管理")
public class FittingController {

    @Autowired
    private IFittingService iFittingService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private FittingExcelReadListener fittingExcelReadListener;

    /**
     * 分页查询数据
     */
    @ApiOperation(value = "分页查询配件", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "分页查询配件")
    @RequestMapping(value = "/select_paged/{pageSize}/{pageIndex}", method = RequestMethod.POST)
    public RespVO<PagedResult<Fitting>> selectPaged(@ModelAttribute Fitting fitting,
                                                    @PathParam(value = "") Page page) {
        PagedResult<Fitting> pageResult = iFittingService.selectPaged(fitting, page);
        return RespVOBuilder.success(pageResult);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @ApiOperation(value = "查询配件", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "配件ID", dataType = "long", required = true, paramType = "query"),
    })
    @Operation(value = "select_by_id", desc = "查询保养手册项")
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    public RespVO<Fitting> selectByPrimaryKey(@RequestParam Long id) {
        Fitting po = iFittingService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @ApiOperation(value = "删除配件", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "配件ID", dataType = "long", required = true, paramType = "query"),
    })
    @Operation(value = "delete_by_id", desc = "删除配件")
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO deleteByPrimaryKey(@RequestParam Long id) {
        Integer num = iFittingService.deleteByPrimaryKey(id);
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
    @ApiOperation(value = "新增配件", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "save_fitting", desc = "新增配件")
    @RequestMapping(value = "/save_fitting", method = RequestMethod.POST)
    public RespVO insert(@RequestBody Fitting fitting) {
        Integer num = iFittingService.insertSelective(fitting);
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
    @ApiOperation(value = "修改配件", httpMethod = "PUT")
    @ApiImplicitParams({

    })
    @Operation(value = "update_fitting", desc = "修改配件")
    @RequestMapping(value = "/update_fitting", method = RequestMethod.PUT)
    public RespVO updateByPrimaryKeySelective(@RequestBody Fitting fitting) {
        Integer num = iFittingService.updateByPrimaryKeySelective(fitting);
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
    @ApiOperation(value = "查询配件", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "query_list", desc = "查询配件")
    @RequestMapping(value = "/query_list", method = RequestMethod.POST)
    public RespVO<RespDataVO<Fitting>> queryByCondition(@RequestBody Fitting fitting) {
        List<Fitting> list = iFittingService.query(fitting);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "导入配件", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "upload", desc = "导入配件")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    public RespVO upload(@RequestParam MultipartFile multipartFile,
                         @RequestParam String sys) {
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            if (!StringUtils.isEmpty(originalFilename)) {
                // TODO 处理  createBy
                fittingExcelReadListener.setSysAndCreateBy(sys, 1L);
                InputStream inputStream = multipartFile.getInputStream();
                excelService.readExcel(originalFilename, fittingExcelReadListener, inputStream, FittingVo.class, 1);
                return RespVOBuilder.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespVOBuilder.failure("上传失败");
    }


}
