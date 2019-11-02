package com.lego.perception.auth;
import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.CurrentVo;
import com.framework.common.sdto.RespVO;
import com.lego.perception.auth.service.IAuthService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yanglf
 * @description
 * @since 2019/8/27
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AuthServiceApplication.class})
public class AuthServiceTest {

    @Autowired
    private IAuthService iAuthService;

    @Test
    public void test() {
        RespVO<CurrentVo> userToken = iAuthService.getUserToken("1212121");
        Assert.assertEquals(userToken.getRetCode(), RespConsts.SUCCESS_RESULT_CODE);
    }


}
