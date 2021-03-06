package com.lego.perception.system.mapper;

import com.framework.common.page.PagedResult;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.system.model.entity.Project;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/8/26
 **/
@Repository
public interface ProjectMapper extends Mapper<Project> {

    Project selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

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
