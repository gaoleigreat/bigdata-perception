package com.lego.perception.system.service.impl;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.system.model.entity.UserRoleProject;
import com.lego.perception.system.mapper.UserRoleProjectMapper;
import com.lego.perception.system.service.IUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserRoleServiceImpl implements IUserRoleService {

    @Autowired
    private UserRoleProjectMapper userRoleProjectMapper;

    @Override
    public List<UserRoleProject> findList(UserRoleProject userRoleProject) {

        return userRoleProjectMapper.findList(userRoleProject);
    }

    @Override
    public PagedResult<UserRoleProject> findPagedList(UserRoleProject userRoleProject, Page page) {

        return userRoleProjectMapper.findPagedList(userRoleProject, page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespVO insertList(List<UserRoleProject> userRoleProjects) {
        if (CollectionUtils.isEmpty(userRoleProjects)) {
            return RespVOBuilder.failure("参数缺失");
        }
        for (UserRoleProject userRoleProject : userRoleProjects) {
            userRoleProject.setCreateInfo();
        }
        userRoleProjectMapper.insertList(userRoleProjects);
        return RespVOBuilder.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespVO delete(UserRoleProject userRoleProject) {
        if (null == userRoleProject || (null == userRoleProject.getId() && (null == userRoleProject.getUserId() || null == userRoleProject.getRoleId()
                || null == userRoleProject.getProjectId()
        ))) {
            return RespVOBuilder.failure("参数缺失");
        }
        userRoleProjectMapper.delete(userRoleProject);
        return RespVOBuilder.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespVO deleteList(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return RespVOBuilder.failure("参数缺失");
        }
        userRoleProjectMapper.deleteList(ids);
        return RespVOBuilder.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespVO deleteListByUserIds(List<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return RespVOBuilder.failure("参数缺失");
        }
        for (Long userId : userIds) {
            UserRoleProject query = new UserRoleProject();
            query.setUserId(userId);
            List<UserRoleProject> userRoleList = userRoleProjectMapper.findList(query);
            List<Long> ids = new ArrayList<>();
            if (userRoleList != null && userRoleList.size() > 0) {
                for (UserRoleProject userRoleProject : userRoleList) {
                    ids.add(userRoleProject.getId());
                }
                userRoleProjectMapper.deleteList(ids);
            }
        }
        return RespVOBuilder.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespVO save(Long userId, List<UserRoleProject> userRoleProjects) {
        //1.校验
        if (null == userId) {
            return RespVOBuilder.failure("参数缺失");
        }
        //2,查询原始数据
        UserRoleProject queryParam = new UserRoleProject();
        queryParam.setUserId(userId);
        List<UserRoleProject> originList = userRoleProjectMapper.findList(queryParam);

        //3.
        List<UserRoleProject> toInsert = new ArrayList<>();
        List<Long> toDelete = new ArrayList<>();

        List<Long> originIds = new ArrayList<>();
        List<String> originUserIds = new ArrayList<>();
        List<String> newUserIds = new ArrayList<>();

        //如果原始数据是空，新增新数据，返回；
        if(CollectionUtils.isEmpty(originList)){
            userRoleProjectMapper.insertList(userRoleProjects);
            return RespVOBuilder.success();
        }else{
            for(UserRoleProject userRoleProgram : originList){
                originUserIds.add(userRoleProgram.getRoleId()+"$"+userRoleProgram.getProjectId());
                originIds.add(userRoleProgram.getId());
            }
        }

        //如果新数据是空，删除原始数据，返回；
        if (CollectionUtils.isEmpty(userRoleProjects)) {
            userRoleProjectMapper.deleteList(originIds);
            return RespVOBuilder.success();
        } else {
            for (UserRoleProject userRoleProject : userRoleProjects) {
                String key = userRoleProject.getRoleId() + "";
                newUserIds.add(key);
                if (originUserIds.contains(key)) {
                    continue;
                }
                toInsert.add(userRoleProject);
            }
        }

        for (UserRoleProject userRoleProject : originList) {
            String key = userRoleProject.getRoleId() + "";
            if (newUserIds.contains(key)) {
                continue;
            }
            toDelete.add(userRoleProject.getId());
        }

        //保存数据
        if (!CollectionUtils.isEmpty(toInsert)) {
            userRoleProjectMapper.insertList(toInsert);
        }

        if (!CollectionUtils.isEmpty(toDelete)) {
            userRoleProjectMapper.deleteList(toDelete);
        }

        return RespVOBuilder.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespVO updateAndInsert(UserRoleProject userRoleProject) {
        if (null == userRoleProject || null == userRoleProject.getRoleId() || null == userRoleProject.getUserId() || null ==userRoleProject.getProjectId()) {
            return RespVOBuilder.failure("参数缺失");
        }
        List<UserRoleProject> originList = findList(userRoleProject);
        if (CollectionUtils.isEmpty(originList)) {
            insertList(Arrays.asList(userRoleProject));
        }
        return RespVOBuilder.success();
    }
}
