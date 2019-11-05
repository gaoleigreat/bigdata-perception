package com.lego.perception.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.tool.WhereEntityTool;
import com.framework.mybatis.utils.PageUtil;
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
    public PagedResult<Project> selectPaged(Page page, Project project) {
        IPage<Project> iPage = PageUtil.page2IPage(page);
        QueryWrapper<Project> wrapper= new QueryWrapper<>();
        WhereEntityTool.invoke(project,wrapper);
        wrapper.orderByDesc("creation_date");
        IPage selectPage = projectMapper.selectPage(iPage, wrapper);
        return PageUtil.iPage2Result(selectPage);
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
    public Integer insert(Project project, Long userId) {
        project.setCreateInfo(userId);
        return projectMapper.insert(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertSelective(Project project, Long userId) {
        project.setCreateInfo(userId);
        return projectMapper.insertSelective(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertSelectiveIgnore(Project project, Long userId) {
        project.setCreateInfo(userId);
        return projectMapper.insertSelectiveIgnore(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateByPrimaryKeySelective(Project project, Long userId) {
        project.setUpdateInfo(userId);
        return projectMapper.updateByPrimaryKeySelective(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateByPrimaryKey(Project project, Long userId) {
        project.setUpdateInfo(userId);
        return projectMapper.updateByPrimaryKey(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer batchInsert(List<Project> projectList, Long userId) {
        if (!CollectionUtils.isEmpty(projectList)) {
            for (Project project : projectList) {
                project.setCreateInfo(userId);
            }
        }
        return projectMapper.batchInsert(projectList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer batchUpdate(List<Project> projectList, Long userId) {
        if (!CollectionUtils.isEmpty(projectList)) {
            for (Project project : projectList) {
                project.setUpdateInfo(userId);
            }
        }
        return projectMapper.batchUpdate(projectList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer upsert(Project project, Long userId) {
        project.setUpdateInfo(userId);
        return projectMapper.upsert(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer upsertSelective(Project project, Long userId) {
        project.setUpdateInfo(userId);
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
