package com.lego.equipment.service.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.IServiceCommentService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.equipment.model.entity.ServiceComment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-18 03:51:45
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/serviceComment")
@Api(value = "serviceComment", description = "维修评论")
public class ServiceCommentController {
    @Autowired
    private IServiceCommentService iServiceCommentService;

    /**
     * 分页查询数据
     */
    @ApiOperation(value = "查询维修评论", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "查询维修评论")
    @RequestMapping(value = "/select_paged/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public RespVO<PagedResult<ServiceComment>> selectPaged(@ModelAttribute ServiceComment serviceComment,
                                                           @PathParam(value = "") Page page) {
        PagedResult<ServiceComment> pageResult = iServiceCommentService.selectPaged(page, serviceComment);
        return RespVOBuilder.success(pageResult);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @ApiOperation(value = "查询维修评论", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "维修评论ID", dataType = "Long", required = true, paramType = "query"),
    })
    @Operation(value = "select_by_id", desc = "查询维修评论")
    @RequestMapping(value = "/select_by_id",method = RequestMethod.GET)
    public RespVO<ServiceComment> selectByPrimaryKey(@RequestParam Long id) {
        ServiceComment po = iServiceCommentService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @ApiOperation(value = "删除维修评论", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "维修评论ID", dataType = "Long", required = true, paramType = "query"),
    })
    @Operation(value = "delete_by_id", desc = "删除维修评论")
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO deleteByPrimaryKey(@RequestParam Long id) {
        Integer num = iServiceCommentService.deleteByPrimaryKey(id);
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
    @ApiOperation(value = "新增维修评论", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "save_serviceComment", desc = "新增维修评论")
    @RequestMapping("/save_serviceComment")
    public RespVO insert(@RequestBody ServiceComment serviceComment) {
        Integer num = iServiceCommentService.insertSelective(serviceComment);
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
    @ApiOperation(value = "修改维修评论", httpMethod = "PUT")
    @ApiImplicitParams({

    })
    @Operation(value = "update_serviceComment", desc = "修改维修评论")
    @RequestMapping(value = "/update_serviceComment",method = RequestMethod.PUT)
    public RespVO updateByPrimaryKeySelective(@RequestBody ServiceComment serviceComment) {
        Integer num = iServiceCommentService.updateByPrimaryKeySelective(serviceComment);
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
    @ApiOperation(value = "查询维修评论", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "query_list", desc = "查询维修评论")
    @RequestMapping(value = "/query_list", method = RequestMethod.POST)
    public RespVO<RespDataVO<ServiceComment>> queryByCondition(ServiceComment serviceComment) {
        List<ServiceComment> list = iServiceCommentService.query(serviceComment);
        return RespVOBuilder.success(list);
    }

}
