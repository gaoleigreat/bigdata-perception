package com.lego.perception.system;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.lego.framework.system.model.entity.Role;
import com.lego.perception.system.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/9/2
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SystemServiceApplication.class})
@Slf4j
public class RoleServiceTest {

    @Autowired
    private IRoleService iRoleService;


    /**
     * 查询角色列表
     */
    @Test
    public void testFindList() {
        List<Role> list = iRoleService.findList(Role.builder().build());
        log.info("role list :[{}]", list);
    }


    @Test
    public void testInsertList() {
        List<Role> roles = new ArrayList<>();
        Role role1 = Role.builder()
                .roleName("测试角色01")
                .scope("test")
                .status(1)
                .build();
        roles.add(role1);
        Role role2 = Role.builder()
                .roleName("测试角色02")
                .scope("test")
                .status(1)
                .build();
        roles.add(role2);
        RespVO respVO = iRoleService.insertList(roles);
        Role role = Role.builder().build();
        role.setId(role1.getId());
        List<Role> list = iRoleService.findList(role);
        Assert.assertTrue(list.size() > 0);
    }


    @Test
    public void testUpdate() {
        Role role = Role.builder().status(2).build();
        role.setId(1164829915520040963L);
        RespVO respVO = iRoleService.update(role);
        Assert.assertEquals(respVO.getRetCode(), RespConsts.SUCCESS_RESULT_CODE);
    }


    @Test
    public void testDelete() {
        RespVO respVO = iRoleService.delete(1164829915520040963L);
        Assert.assertEquals(respVO.getRetCode(), RespConsts.SUCCESS_RESULT_CODE);
    }


}
