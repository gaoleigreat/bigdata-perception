package com.lego.perception.system.service.impl;

import com.lego.framework.base.page.PagedResult;
import com.lego.framework.system.model.entity.Project;
import com.lego.perception.system.mapper.ProjectMapper;
import com.lego.perception.system.service.IProjectService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/8/26
 **/
@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public PagedResult<Project> selectPaged(RowBounds rowBounds) {
        return projectMapper.selectPaged(rowBounds);
    }

    @Override
    public Project selectByPrimaryKey(Long id) {
        return projectMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByPrimaryKey(Long id) {
        return projectMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insert(Project project) {
        project.setCreateInfo();
        return projectMapper.insert(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertSelective(Project project) {
        project.setCreateInfo();
        return projectMapper.insertSelective(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertSelectiveIgnore(Project project) {
        project.setCreateInfo();
        return projectMapper.insertSelectiveIgnore(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateByPrimaryKeySelective(Project project) {
        project.setUpdateInfo();
        return projectMapper.updateByPrimaryKeySelective(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateByPrimaryKey(Project project) {
        project.setUpdateInfo();
        return projectMapper.updateByPrimaryKey(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer batchInsert(List<Project> projectList) {
        if (!CollectionUtils.isEmpty(projectList)) {
            for (Project project : projectList) {
                project.setCreateInfo();
            }
        }
        return projectMapper.batchInsert(projectList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer batchUpdate(List<Project> projectList) {
        if (!CollectionUtils.isEmpty(projectList)) {
            for (Project project : projectList) {
                project.setUpdateInfo();
            }
        }
        return projectMapper.batchUpdate(projectList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer upsert(Project project) {
        project.setUpdateInfo();
        return projectMapper.upsert(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer upsertSelective(Project project) {
        project.setUpdateInfo();
        return projectMapper.upsertSelective(project);
    }

    @Override
    public List<Project> query(Project project) {
        return projectMapper.query(project);
    }

    @Override
    public Long queryTotal() {
        return projectMapper.queryTotal();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteBatch(List<Long> list) {
        List<Project> projects = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Long id : list) {
                Project project = projectMapper.selectByPrimaryKey(id);
                if (project != null) {
                    project.setDeleteFlag(2);
                    projects.add(project);
                }
            }
        }
        return projectMapper.batchUpdate(projects);
    }
}
