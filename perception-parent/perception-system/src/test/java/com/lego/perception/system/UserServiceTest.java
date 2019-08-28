package com.lego.perception.system;

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

    @Test
    public void findPagedList() {
        User user = User.builder().build();
        List<User> userList = iUserService.findList(user);
        log.info("userList:{}", userList);
    }

}
