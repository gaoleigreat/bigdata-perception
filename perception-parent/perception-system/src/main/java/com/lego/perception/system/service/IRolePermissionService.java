package com.lego.perception.system.service;
import com.lego.framework.system.model.entity.RolePermission;
import com.survey.lib.common.vo.RespVO;
import java.util.List;

/**
 * 角色权限服务
 * weihao 2018-08-18
 */
public interface IRolePermissionService {

    /**
     * 查询角色权限列表
     * @param rolePermission
     * @return
     */
    List<RolePermission> findList(RolePermission rolePermission);

    /**
     * 批量新增角色权限
     * @param rolePermissions
     * @return
     */
    RespVO insertList(List<RolePermission> rolePermissions);

    /**
     * 批量删除角色权限
     * @param ids
     * @return
     */
    RespVO deleteList(List<Long> ids);

    /**
     * 根据权限id删除
     * @param permissionIds
     * @return
     */
    RespVO deleteByPermissionIds(List<Long> permissionIds);

    /**
     * 增量更新角色
     * @param roleId
     * @param rolePermissions
     * @return
     */
    RespVO save(Long roleId, List<RolePermission> rolePermissions);
}
