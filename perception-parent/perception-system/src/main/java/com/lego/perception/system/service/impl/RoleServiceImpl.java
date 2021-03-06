package com.lego.perception.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.mybatis.tool.WhereEntityTool;
import com.framework.mybatis.utils.PageUtil;
import com.lego.perception.system.mapper.RoleMapper;
import com.lego.perception.system.service.IRoleService;
import com.lego.framework.system.model.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yanglf
 */
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findList(Role role) {
        return roleMapper.findList(role);
    }

    @Override
    public List<Role> findcadreList(Role role) {
        List<Role> roleList = roleMapper.findList(role);
        if (roleList == null || roleList.size() <= 0) {
            return null;
        }
        for (int i = 0; i < roleList.size(); i++) {
            Role role1 = roleList.get(i);
            if (role1.getId() == 2) {
                roleList.remove(i);
            }
        }
        return roleList;
    }

    @Override
    public PagedResult<Role> findPagedList(Role role, Page page) {
        IPage<Role> iPage = PageUtil.page2IPage(page);
        QueryWrapper<Role> wrapper = new QueryWrapper();
        WhereEntityTool.invoke(role, wrapper);
        wrapper.orderByDesc("creation_date");
        IPage selectPage = roleMapper.selectPage(iPage, wrapper);
        return PageUtil.iPage2Result(selectPage);
    }

    @Override
    public RespVO insert(Role role) {
        if (null == role) {
            return RespVOBuilder.failure("参数缺失");
        }
        role.setCreateInfo();
        roleMapper.save(role);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO insertList(List<Role> roles) {
        if (CollectionUtils.isEmpty(roles)) {
            return RespVOBuilder.failure("参数缺失");
        }
        for (Role role : roles) {
            role.setCreateInfo();
        }
        roleMapper.insertList(roles);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO update(Role role) {
        if (null == role) {
            return RespVOBuilder.failure("参数缺失");
        }
        role.setUpdateInfo();
        roleMapper.update(role);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO updateList(List<Role> roles) {
        if (CollectionUtils.isEmpty(roles)) {
            return RespVOBuilder.failure("参数缺失");
        }
        for (Role role : roles) {
            role.setUpdateInfo();
        }
        roleMapper.updateList(roles);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO delete(Long id) {
        if (null == id) {
            return RespVOBuilder.failure("参数缺失");
        }
        roleMapper.delete(id);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO deleteList(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return RespVOBuilder.failure("参数缺失");
        }
        roleMapper.deleteList(ids);
        return RespVOBuilder.success();
    }
}
