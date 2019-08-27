package com.lego.perception.system.controller;

import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.page.PagedResult;
import com.lego.framework.base.sdto.RespDataVO;
import com.lego.framework.base.sdto.RespVO;
import com.lego.framework.base.sdto.RespVOBuilder;
import com.lego.framework.system.model.entity.Menu;
import com.lego.perception.system.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yanglf
 */
@RestController
@RequestMapping("/menu")
@Resource(value = "menu", desc = "菜单管理")
@Api(value = "MenuController", description = "菜单管理")
@Slf4j
public class MenuController {

    @Autowired
    private IMenuService iMenuService;

    /**
     * 分页查询数据
     */
    @RequestMapping(value = "/select_paged", method = RequestMethod.GET)
    @Operation(value = "select_paged", desc = "分页查询")
    @ApiOperation("分页查询")
    public RespVO<PagedResult<Menu>> selectPaged(RowBounds rowBounds) {
        PagedResult<Menu> page = iMenuService.selectPaged(rowBounds);
        return RespVOBuilder.success(page);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    @Operation(value = "select_by_id", desc = "根据ID查询菜单")
    @ApiOperation("根据ID查询菜单")
    public RespVO<Menu> selectByPrimaryKey(Long id) {
        Menu po = iMenuService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    @Operation(value = "delete_by_id", desc = "删除菜单")
    @ApiOperation("删除菜单")
    public RespVO deleteByPrimaryKey(Long id) {
        Integer num = iMenuService.deleteByPrimaryKey(id);
        return RespVOBuilder.success();
    }

    /**
     * 新增数据
     *
     * @return
     */
    @RequestMapping(value = "/save_tplMenu", method = RequestMethod.POST)
    @Operation(value = "save_tplMenu", desc = "新增菜单")
    @ApiOperation("新增菜单")
    public RespVO insert(@RequestBody Menu menu) {
        Integer num = iMenuService.insertSelective(menu);
        return RespVOBuilder.success();
    }

    /**
     * 修改数据
     *
     * @return
     */
    @RequestMapping(value = "/update_tplMenu", method = RequestMethod.PUT)
    @Operation(value = "update_tplMenu", desc = "修改菜单")
    @ApiOperation("修改菜单")
    public RespVO updateByPrimaryKeySelective(@ModelAttribute Menu menu) {
        Integer num = iMenuService.updateByPrimaryKeySelective(menu);
        return RespVOBuilder.success();
    }


    /**
     * 查询列表
     *
     * @return
     */
    @RequestMapping(value = "/query_list", method = RequestMethod.GET)
    @Operation(value = "query_list", desc = "查询列表")
    @ApiOperation("查询列表")
    public RespVO<RespDataVO<Menu>> queryByCondition(@ModelAttribute Menu menu) {
        List<Menu> list = iMenuService.query(menu);
        return RespVOBuilder.success(list);
    }

}
