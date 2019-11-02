package com.lego.framework.base.service.impl;
import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.CurrentVo;
import com.framework.common.sdto.RespVO;
import com.lego.framework.auth.feign.AuthClient;
import com.lego.framework.base.service.IAuthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yanglf
 */
@Service
public class AuthCheckServiceImpl implements IAuthCheckService {

    @Autowired
    private AuthClient authClient;


    @Override
    public CurrentVo getData(String token) {

        RespVO<CurrentVo> currentVoRespVO = authClient.getUserToken(token);
        if(currentVoRespVO.getRetCode()== RespConsts.SUCCESS_RESULT_CODE){
            return currentVoRespVO.getInfo();
        }
        return null;
    }

}
