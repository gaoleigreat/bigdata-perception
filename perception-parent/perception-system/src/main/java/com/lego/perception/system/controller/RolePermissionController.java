package com.lego.perception.system.controller;
import com.framework.common.sdto.RespVO;
import com.lego.framework.base.annotation.Resource;
import com.lego.perception.system.service.IRolePermissionService;
import com.lego.framework.system.model.entity.RolePermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rolePermission/v1")
@Resource(value = "rolePermission", desc = "角色权限点")
@Api(value = "RolePermissionController",description = "角色权限点管理")
@Slf4j
public class RolePermissionController {

    @Autowired
    private IRolePermissionService rolePermissionService;

    @RequestMapping(value="/findList", method=RequestMethod.GET)
    @ApiOperation("查询")
    public List<RolePermission> findList(@ModelAttribute RolePermission rolePermission){

        return rolePermissionService.findList(rolePermission);
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ApiOperation("更新")
    public RespVO save(@RequestParam("")Long roleId, @RequestBody List<RolePermission> rolePermissions){

        return rolePermissionService.save(roleId, rolePermissions);
    }
}
