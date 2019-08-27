package com.lego.perception.system.controller;
import com.framework.common.sdto.RespVO;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.perception.system.service.IPermissionService;
import com.lego.framework.system.model.entity.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permission/v1")
@Resource(value="permission", desc="权限管理")
@Api(value="PermissionController",description = "权限点管理")
@Slf4j
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询列表")
    public List<Permission> findList(@ModelAttribute Permission permission){

        return permissionService.findList(permission);
    }

    @RequestMapping(value = "/findTree", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询权限点数结构")
    public List<Map<String, Object>> findTree(@ModelAttribute Permission permission){

        return permissionService.findTree(permission);
    }

    @RequestMapping(value = "/insertList", method = RequestMethod.POST)
    @Operation(value = "add", desc = "新增")
    @ApiOperation("批量新增")
    public RespVO insertList(@RequestBody List<Permission> permissions){

        return permissionService.insertList(permissions);
    }

    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    @Operation(value = "delete", desc = "删除")
    @ApiOperation("批量删除")
    public RespVO deleteList(@RequestBody List<Long> ids){

        return permissionService.deleteList(ids);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation("更新")
    public RespVO save(@RequestParam("scope")String scope, @RequestBody List<Permission> permissions){

        return permissionService.save(scope, permissions);
    }
}
