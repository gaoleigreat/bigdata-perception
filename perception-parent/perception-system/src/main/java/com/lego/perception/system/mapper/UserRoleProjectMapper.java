package com.lego.perception.system.mapper;

import com.lego.framework.base.page.Page;
import com.lego.framework.base.page.PagedResult;
import com.lego.framework.system.model.entity.User;
import com.lego.framework.system.model.entity.UserRoleProject;
import com.lego.survey.lib.mybatis.mapper.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yanglf
 */
@Repository
public interface UserRoleProjectMapper extends Mapper<UserRoleProject> {

    /**
     * 查询列表
     *
     * @param userRoleProject
     * @return
     */
    List<UserRoleProject> findList(UserRoleProject userRoleProject);

    /**
     * 分页查询
     *
     * @param userRoleProject
     * @param page
     * @return
     */
    PagedResult<UserRoleProject> findPagedList(UserRoleProject userRoleProject, Page page);

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
     * @param userRoleProject
     * @param user
     * @param page
     * @return
     */
    PagedResult<User> findPagedList(UserRoleProject userRoleProject, User user, Page page);

    /**
     * 新增列表
     *
     * @param userRoleProjects
     * @return
     */
    Integer insertList(List<UserRoleProject> userRoleProjects);

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
     * @param userRoleProject
     * @return
     */
    Integer delete(UserRoleProject userRoleProject);
}
