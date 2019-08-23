package com.lego.perception.system.service.impl;

import com.lego.perception.system.mapper.RoleMapper;
import com.lego.perception.system.service.IRoleService;
import com.lego.framework.system.model.entity.Role;
import com.survey.lib.common.page.Page;
import com.survey.lib.common.page.PagedResult;
import com.survey.lib.common.vo.RespVO;
import com.survey.lib.common.vo.RespVOBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findList(Role role) {

        return roleMapper.findList(role);
    }

    @Override
    public List<Role> findcadreList(Role role) {
        List<Role> roleList = roleMapper.findList(role);
        if(roleList==null || roleList.size()<=0){
            return null;
        }
        for(int i = 0 ;i<roleList.size();i++){
            Role role1 = roleList.get(i);
            if(role1.getId()==2){
                roleList.remove(i);
            }
        }
        return roleList;
    }

    @Override
    public PagedResult<Role> findPagedList(Role role, Page page) {

        return roleMapper.findPagedList(role, page);
    }

    @Override
    public RespVO insert(Role role) {
        if(null == role){
            return RespVOBuilder.failure("参数缺失");
        }
        role.setCreateInfo();
        roleMapper.insert(role);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO insertList(List<Role> roles) {
        if(CollectionUtils.isEmpty(roles)){
            return RespVOBuilder.failure("参数缺失");
        }
        for(Role role : roles){
            role.setCreateInfo();
        }
        roleMapper.insertList(roles);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO update(Role role) {
        if(null == role){
            return RespVOBuilder.failure("参数缺失");
        }
        role.setUpdateInfo();
        roleMapper.update(role);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO updateList(List<Role> roles) {
        if(CollectionUtils.isEmpty(roles)){
            return RespVOBuilder.failure("参数缺失");
        }
        for(Role role : roles){
            role.setUpdateInfo();
        }
        roleMapper.updateList(roles);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO delete(Long id) {
        if(null == id){
            return RespVOBuilder.failure("参数缺失");
        }
        roleMapper.delete(id);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO deleteList(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return RespVOBuilder.failure("参数缺失");
        }
        roleMapper.deleteList(ids);
        return RespVOBuilder.success();
    }
}
