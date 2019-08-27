package com.lego.perception.system.controller;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.system.model.entity.UserRoleProject;
import com.lego.perception.system.service.IUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/userRole/v1")
@Resource(value="userRole", desc="用户权限")
@Api(value="UserRoleController",description = "用户权限管理")
@Slf4j
public class UserRoleProjectController {

    @Autowired
    private IUserRoleService userRoleService;

    @RequestMapping(value="/findList", method=RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询")
    public List<UserRoleProject> findList(@RequestParam("userId")Long userId,
                                          @RequestParam(value = "roleId",required = false)Long roleId,
                                          @RequestParam(value = "projectId",required = false)Long projectId){
        UserRoleProject userRoleProject=new UserRoleProject();
        userRoleProject.setUserId(userId);
        userRoleProject.setRoleId(roleId);
        userRoleProject.setProjectId(projectId);
        return userRoleService.findList(userRoleProject);
    }

    @RequestMapping(value="/findPagedList/{pageSize}/{curPage}", method=RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("分页查询")
    public PagedResult<UserRoleProject> findPagedList(@ModelAttribute UserRoleProject userRoleProject, @PathParam("") Page page){

        return userRoleService.findPagedList(userRoleProject, page);
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @Operation(value = "save", desc = "更新")
    @ApiOperation("更新")
    public RespVO save(@RequestParam("")Long userId, @RequestBody List<UserRoleProject> userRoleProjects){

        return userRoleService.save(userId, userRoleProjects);
    }

    @RequestMapping(value="/delete", method = RequestMethod.POST)
    @Operation(value = "save", desc = "更新")
    @ApiOperation("更新")
    public RespVO save(@RequestBody UserRoleProject userRoleProject){

        return userRoleService.delete(userRoleProject);
    }

    @RequestMapping(value="/updateAndInsert", method = RequestMethod.POST)
    @ApiOperation("新增用户权限，规则是存在更新，不存在新增")
    public RespVO updateAndInsert(@RequestBody UserRoleProject userRoleProject){

        return userRoleService.updateAndInsert(userRoleProject);
    }
}
