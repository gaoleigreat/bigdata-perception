package com.lego.perception.system.service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lego.framework.base.page.PagedResult;
import com.lego.framework.system.model.entity.Project;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author yanglf
 */
public interface IProjectService {


    PagedResult<Project> selectPaged(RowBounds rowBounds);

    Project selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(Project project);

    Integer insertSelective(Project project);

    Integer insertSelectiveIgnore(Project project);

    Integer updateByPrimaryKeySelective(Project project);

    Integer updateByPrimaryKey(Project project);

    Integer batchInsert(List<Project> projectList);

    Integer batchUpdate(List<Project> projectList);

    /**
     * 存在即更新
     *
     * @param project
     * @return
     */
    Integer upsert(Project project);

    /**
     * 存在即更新，可选择具体属性
     *
     * @param project
     * @return
     */
    Integer upsertSelective(Project project);

    List<Project> query(Project project);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}
