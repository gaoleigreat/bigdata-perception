package com.lego.perception.system.service;
import com.lego.framework.system.model.UserRole;
import com.survey.lib.common.page.Page;
import com.survey.lib.common.page.PagedResult;
import com.survey.lib.common.vo.RespVO;
import java.util.List;

public interface IUserRoleService {

    /**
     * 查询列表
     * @param userRole
     * @return
     */
    List<UserRole> findList(UserRole userRole);

    /**
     * 分页查询
     * @param userRole
     * @param page
     * @return
     */
    PagedResult<UserRole> findPagedList(UserRole userRole, Page page);

    /**
     * 新增列表
     * @param userRoles
     * @return
     */
    RespVO insertList(List<UserRole> userRoles);

    /**
     * 删除
     * @param userRole
     * @return
     */
    RespVO delete(UserRole userRole);

    /**
     * 删除类表
     * @param ids
     * @return
     */
    RespVO deleteList(List<Long> ids);

    /**
     * 删除，根据用户id删除
     * @param userIds
     * @return
     */
    RespVO deleteListByUserIds(List<Long> userIds);

    /**
     *  增量更新
     * @param userId
     * @return
     */
    RespVO save(Long userId, List<UserRole> userRoles);

    /**
     * 更新或者更新，存在更新，不存在新增
     * @param userRole
     * @return
     */
    RespVO updateAndInsert(UserRole userRole);
}
