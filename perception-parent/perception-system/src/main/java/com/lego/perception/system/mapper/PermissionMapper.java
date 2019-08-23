package com.lego.perception.system.mapper;
import com.lego.survey.lib.mybatis.mapper.Mapper;
import com.lego.framework.system.model.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限点
 */
@Repository
public interface PermissionMapper extends Mapper<Permission> {

    /**
     * 查询权限点
     * @return
     */
    List<Permission> findList(Permission permission);

    /**
     * 新增权限点
     * @param permissions
     * @return
     */
    Integer insertList(List<Permission> permissions);

    /**
     * 删除权限点
     * @param ids
     * @return
     */
    Integer deleteList(List<Long> ids);

    /**
     * 查询用户权限
     * @param userId
     * @return
     */
    List<Permission> findUserPermissions(Long userId);
}
