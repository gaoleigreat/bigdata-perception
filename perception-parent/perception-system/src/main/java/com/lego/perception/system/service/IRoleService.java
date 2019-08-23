package com.lego.perception.system.service;
import com.lego.framework.system.model.entity.Role;
import com.survey.lib.common.page.Page;
import com.survey.lib.common.page.PagedResult;
import com.survey.lib.common.vo.RespVO;
import java.util.List;

/**
 * 角色服务
 * weihao 2018-08-15
 */
public interface IRoleService {

    /**
     * 查询角色列表
     * @param role
     * @return
     */
    List<Role> findList(Role role);

    /**
     * 帮扶干部（除信息员）列表
     * @param role
     * @return
     */
    List<Role> findcadreList(Role role);

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
    RespVO insert(Role role);

    /**
     * 批量新增角色
     * @param roles
     * @return
     */
    RespVO insertList(List<Role> roles);

    /**
     * 更新角色
     * @param role
     * @return
     */
    RespVO update(Role role);

    /**
     * 批量更新角色
     * @param roles
     * @return
     */
    RespVO updateList(List<Role> roles);

    /**
     * 删除角色
     * @param id
     * @return
     */
    RespVO delete(Long id);

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    RespVO deleteList(List<Long> ids);


}
