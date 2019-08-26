package com.lego.perception.system.service;
import com.lego.framework.base.page.Page;
import com.lego.framework.base.page.PagedResult;
import com.lego.framework.base.sdto.RespVO;
import com.lego.framework.system.model.entity.UserRoleProject;
import java.util.List;

public interface IUserRoleService {

    /**
     * 查询列表
     * @param userRoleProject
     * @return
     */
    List<UserRoleProject> findList(UserRoleProject userRoleProject);

    /**
     * 分页查询
     * @param userRoleProject
     * @param page
     * @return
     */
    PagedResult<UserRoleProject> findPagedList(UserRoleProject userRoleProject, Page page);

    /**
     * 新增列表
     * @param userRoleProjects
     * @return
     */
    RespVO insertList(List<UserRoleProject> userRoleProjects);

    /**
     * 删除
     * @param userRoleProject
     * @return
     */
    RespVO delete(UserRoleProject userRoleProject);

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
    RespVO save(Long userId, List<UserRoleProject> userRoleProjects);

    /**
     * 更新或者更新，存在更新，不存在新增
     * @param userRoleProject
     * @return
     */
    RespVO updateAndInsert(UserRoleProject userRoleProject);
}
