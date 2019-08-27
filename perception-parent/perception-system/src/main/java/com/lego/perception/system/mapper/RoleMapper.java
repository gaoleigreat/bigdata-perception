package com.lego.perception.system.mapper;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.system.model.entity.Role;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {

    /**
     * 查询角色列表
     * @param role
     * @return
     */
    List<Role> findList(Role role);

    /**
     * 分页查询角色
     * @param role
     * @param page
     * @return
     */
    PagedResult<Role> findPagedList(Role role, Page page);

    /**
     * 新增角色
     * @param role
     * @return
     */
    Integer save(Role role);

    /**
     * 批量新增角色
     * @param roles
     * @return
     */
    Integer insertList(List<Role> roles);

    /**
     * 更新角色
     * @param role
     * @return
     */
    Integer update(Role role);

    /**
     * 批量更新角色
     * @param roles
     * @return
     */
    Integer updateList(List<Role> roles);

    /**
     * 删除角色
     * @param id
     * @return
     */
    Integer delete(Long id);

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    Integer deleteList(List<Long> ids);
}
