package com.lego.perception.system.service.impl;
import com.lego.perception.system.mapper.RolePermissionMapper;
import com.lego.perception.system.service.IRolePermissionService;
import com.lego.framework.system.model.entity.RolePermission;
import com.survey.lib.common.vo.RespVO;
import com.survey.lib.common.vo.RespVOBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RolePermissionServiceImpl implements IRolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<RolePermission> findList(RolePermission rolePermission) {

        return rolePermissionMapper.findList(rolePermission);
    }

    @Override
    public RespVO insertList(List<RolePermission> rolePermissions) {
        if(CollectionUtils.isEmpty(rolePermissions)){
            return RespVOBuilder.failure("参数缺失");
        }
        for(RolePermission rolePermission : rolePermissions){
            rolePermission.setCreateInfo();
        }
        rolePermissionMapper.insertList(rolePermissions);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO deleteList(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return RespVOBuilder.failure("参数缺失");
        }
        rolePermissionMapper.deleteList(ids);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO deleteByPermissionIds(List<Long> permissionIds) {
        return null;
    }

    @Override
    public RespVO save(Long roleId, List<RolePermission> rolePermissions) {
        //1.校验
        if(null == roleId){
            return RespVOBuilder.failure("参数缺失");
        }
        //2,查询原始数据
        RolePermission queryParam = new RolePermission();
        queryParam.setRoleId(roleId);
        List<RolePermission> originList = rolePermissionMapper.findList(queryParam);

        //3.
        List<RolePermission> toInsert = new ArrayList<>();
        List<Long> toDelete = new ArrayList<>();

        List<Long> originIds = new ArrayList<>();
        List<Long> originPermissionIds = new ArrayList<>();
        List<Long> newUserIds = new ArrayList<>();

        for(RolePermission rolePermission : rolePermissions){
            rolePermission.setRoleId(roleId);
        }

        //如果原始数据是空，新增新数据，返回；
        if(CollectionUtils.isEmpty(originList)){
            rolePermissionMapper.insertList(rolePermissions);
            return RespVOBuilder.success();
        }else{
            for(RolePermission rolePermission : originList){
                originPermissionIds.add(rolePermission.getPermissionId());
                originIds.add(rolePermission.getId());
            }
        }

        //如果新数据是空，删除原始数据，返回；
        if(CollectionUtils.isEmpty(rolePermissions)){
            rolePermissionMapper.deleteList(originIds);
            return RespVOBuilder.success();
        }else{
            for(RolePermission rolePermission : rolePermissions){
                newUserIds.add(rolePermission.getPermissionId());
                if(originPermissionIds.contains(rolePermission.getPermissionId())){
                    continue;
                }
                toInsert.add(rolePermission);
            }
        }

        for(RolePermission rolePermission : originList){
            if(newUserIds.contains(rolePermission.getPermissionId())){
                continue;
            }
            toDelete.add(rolePermission.getId());
        }

        //保存数据
        if(!CollectionUtils.isEmpty(toInsert)){
            rolePermissionMapper.insertList(toInsert);
        }

        if(!CollectionUtils.isEmpty(toDelete)){
            rolePermissionMapper.deleteList(toDelete);
        }

        return RespVOBuilder.success();
    }
}
