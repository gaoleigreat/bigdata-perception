package com.lego.perception.system.service;
import com.framework.common.sdto.RespVO;
import com.lego.framework.system.model.entity.Permission;
import java.util.List;
import java.util.Map;

public interface IPermissionService {

    /**
     * 查询权限点
     * @return
     */
    List<Permission> findList(Permission permission);

    /**
     * 查询权限点
     * @return
     */
    List<Map<String, Object>> findTree(Permission permission);

    /**
     * 新增权限点
     * @param permissions
     * @return
     */
    RespVO insertList(List<Permission> permissions);

    /**
     * 删除权限点
     * @param ids
     * @return
     */
    RespVO deleteList(List<Long> ids);

    /**
     * 增量更新
     * @param scope
     * @param permissions
     * @return
     */
    RespVO save(String scope, List<Permission> permissions);

    /**
     * 根据userId查询用户权限
     *
     * @param userId
     * @return
     */
    List<Permission> findUserPermissions(Long userId);
}
