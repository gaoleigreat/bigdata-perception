package com.lego.perception.system.mapper;
import com.lego.survey.lib.mybatis.mapper.Mapper;
import com.lego.framework.system.model.RolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionMapper extends Mapper<RolePermission> {

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
   Integer insertList(List<RolePermission> rolePermissions);

    /**
     * 批量删除角色权限
     * @param ids
     * @return
     */
    Integer deleteList(List<Long> ids);

    /**
     * 根据权限id删除
     * @param permissionIds
     * @return
     */
    Integer deleteByPermissionIds(List<Long> permissionIds);

}
