package com.lego.perception.system.controller;

import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.system.model.entity.Project;
import com.lego.perception.system.service.IProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * @author yanglf
 */
@RestController
@RequestMapping("/project")
@Resource(value="project", desc="项目管理")
@Api(value="ProjectController",description = "项目管理")
@Slf4j
public class ProjectController {

    @Autowired
    private IProjectService iProjectService;

    /**
     * 分页查询数据
     */
    @RequestMapping(value = "/select_paged",method = RequestMethod.GET)
    @Operation(value = "select_paged", desc = "分页查询")
    @ApiOperation("分页查询")
    public RespVO<PagedResult<Project>> selectPaged(RowBounds rowBounds) {
        PagedResult<Project> page = iProjectService.selectPaged(rowBounds);
        return RespVOBuilder.success(page);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @RequestMapping(value = "/select_by_id",method = RequestMethod.GET)
    @Operation(value = "select_by_id", desc = "根据ID查询项目")
    @ApiOperation("根据ID查询项目")
    public RespVO<Project> selectByPrimaryKey(Long id) {
        Project po = iProjectService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @RequestMapping(value = "/delete_by_id",method = RequestMethod.DELETE)
    @Operation(value = "delete_by_id", desc = "删除项目")
    @ApiOperation("删除项目")
    public RespVO deleteByPrimaryKey(Long id) {
        Integer num = iProjectService.deleteByPrimaryKey(id);
        return RespVOBuilder.success();
    }

    /**
     * 新增数据
     *
     * @return
     */
    @RequestMapping(value = "/save_tplProject",method = RequestMethod.POST)
    @Operation(value = "save_tplProject", desc = "新增项目")
    @ApiOperation("新增项目")
    public RespVO insert(@RequestBody Project project, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Integer num = iProjectService.insertSelective(project,Long.valueOf(userId));
        return RespVOBuilder.success();
    }

    /**
     * 修改数据
     *
     * @return
     */
    @RequestMapping(value = "/update_tplProject",method = RequestMethod.PUT)
    @Operation(value = "update_tplProject", desc = "修改项目")
    @ApiOperation("修改项目")
    public RespVO updateByPrimaryKeySelective(@ModelAttribute Project project,HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Integer num = iProjectService.updateByPrimaryKeySelective(project,Long.valueOf(userId));
        return RespVOBuilder.success();
    }


    /**
     * 查询列表
     *
     * @return
     */
    @RequestMapping(value = "/query_list",method = RequestMethod.GET)
    @Operation(value = "query_list", desc = "查询列表")
    @ApiOperation("查询列表")
    public RespVO<RespDataVO<Project>> queryByCondition(@ModelAttribute Project project) {
        List<Project> list = iProjectService.query(project);
        return RespVOBuilder.success(list);
    }

}
