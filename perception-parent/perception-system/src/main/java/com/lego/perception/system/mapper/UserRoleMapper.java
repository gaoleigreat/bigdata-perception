package com.lego.perception.system.mapper;

import com.lego.survey.lib.mybatis.mapper.Mapper;
import com.lego.framework.system.model.entity.User;
import com.lego.framework.system.model.entity.UserRole;
import com.survey.lib.common.page.Page;
import com.survey.lib.common.page.PagedResult;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRoleMapper extends Mapper<UserRole> {

    /**
     * 查询列表
     *
     * @param userRole
     * @return
     */
    List<UserRole> findList(UserRole userRole);

    /**
     * 分页查询
     *
     * @param userRole
     * @param page
     * @return
     */
    PagedResult<UserRole> findPagedList(UserRole userRole, Page page);

    /**
     * 分页查询某种权限的 用户
     *
     * @param map
     * @param page
     * @return
     */
    PagedResult<User> findPagedUserList(Map<String, Object> map, Page page);

    /**
     * 分页查询某种角色的用户列表
     *
     * @param userRole
     * @param user
     * @param page
     * @return
     */
    PagedResult<User> findPagedList(UserRole userRole, User user, Page page);

    /**
     * 新增列表
     *
     * @param userRoles
     * @return
     */
    Integer insertList(List<UserRole> userRoles);

    /**
     * 删除类表
     *
     * @param ids
     * @return
     */
    Integer deleteList(List<Long> ids);

    /**
     * 根据用户id删除 用户权限
     *
     * @param userIds
     * @return
     */
    Integer deleteListByUserIds(List<Long> userIds);

    /**
     * 删除用户权限
     *
     * @param userRole
     * @return
     */
    Integer delete(UserRole userRole);
}
