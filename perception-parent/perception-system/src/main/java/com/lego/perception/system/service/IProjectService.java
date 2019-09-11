package com.lego.perception.system.service;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.system.model.entity.Project;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author yanglf
 */
public interface IProjectService {


    PagedResult<Project> selectPaged(Page page,Project project);

    Project selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(Project project, Long userId);

    Integer insertSelective(Project project, Long userId);

    Integer insertSelectiveIgnore(Project project, Long userId);

    Integer updateByPrimaryKeySelective(Project project, Long userId);

    Integer updateByPrimaryKey(Project project, Long userId);

    Integer batchInsert(List<Project> projectList, Long userId);

    Integer batchUpdate(List<Project> projectList, Long userId);

    /**
     * 存在即更新
     *
     * @param project
     * @return
     */
    Integer upsert(Project project, Long userId);

    /**
     * 存在即更新，可选择具体属性
     *
     * @param project
     * @return
     */
    Integer upsertSelective(Project project, Long userId);

    List<Project> query(Project project);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}
