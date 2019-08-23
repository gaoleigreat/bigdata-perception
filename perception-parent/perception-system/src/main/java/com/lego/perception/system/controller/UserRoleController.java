package com.lego.perception.system.controller;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.perception.system.service.IUserRoleService;
import com.lego.framework.system.model.UserRole;
import com.survey.lib.common.page.Page;
import com.survey.lib.common.page.PagedResult;
import com.survey.lib.common.vo.RespVO;
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
public class UserRoleController {

    @Autowired
    private IUserRoleService userRoleService;

    @RequestMapping(value="/findList", method=RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询")
    public List<UserRole> findList(@RequestParam("userId")Long userId, @RequestParam(value = "roleId",required = false)Long roleId,
                                          @RequestParam(value = "programId",required = false)Long programId){
        UserRole userRoleProgram=new UserRole();
        userRoleProgram.setUserId(userId);
        userRoleProgram.setRoleId(roleId);
        return userRoleService.findList(userRoleProgram);
    }

    @RequestMapping(value="/findPagedList/{pageSize}/{curPage}", method=RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("分页查询")
    public PagedResult<UserRole> findPagedList(@ModelAttribute UserRole userRole, @PathParam("") Page page){

        return userRoleService.findPagedList(userRole, page);
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @Operation(value = "save", desc = "更新")
    @ApiOperation("更新")
    public RespVO save(@RequestParam("")Long userId, @RequestBody List<UserRole> userRolePrograms){

        return userRoleService.save(userId, userRolePrograms);
    }

    @RequestMapping(value="/delete", method = RequestMethod.POST)
    @Operation(value = "save", desc = "更新")
    @ApiOperation("更新")
    public RespVO save(@RequestBody UserRole userRole){

        return userRoleService.delete(userRole);
    }

    @RequestMapping(value="/updateAndInsert", method = RequestMethod.POST)
    @ApiOperation("新增用户权限，规则是存在更新，不存在新增")
    public RespVO updateAndInsert(@RequestBody UserRole userRole){

        return userRoleService.updateAndInsert(userRole);
    }
}
