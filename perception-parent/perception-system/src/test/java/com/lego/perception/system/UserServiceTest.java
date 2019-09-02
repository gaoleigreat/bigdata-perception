package com.lego.perception.system;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.lego.framework.base.utils.SecurityUtils;
import com.lego.framework.system.model.entity.User;
import com.lego.perception.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/8/27
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SystemServiceApplication.class})
@Slf4j
public class UserServiceTest {

    @Autowired
    private IUserService iUserService;

    /**
     * 查询用户列表
     */
    @Test
    public void testFindList() {
        User user = User.builder().build();
        List<User> userList = iUserService.findList(user);
        log.info("userList:{}", userList);
    }


    /**
     * 新增用户
     */
    @Test
    public void testInsertUser() {
        User user = User.builder()
                .username("admin123")
                .password(SecurityUtils.encryptionWithMd5("111111"))
                .gender(1)
                .idCardNO("61052402021250201")
                .mail("185602541@163.com")
                .phone("1528963021")
                .realName("张三")
                .build();
        RespVO insert = iUserService.insert(user, 1L);
        Assert.assertEquals(insert.getRetCode(), RespConsts.SUCCESS_RESULT_CODE);
    }


    /**
     * 根据ID查用户信息
     */
    @Test
    public void testFindById() {
        iUserService.findById(User.builder().id(1L).build());

    }


}
