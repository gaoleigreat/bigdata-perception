package com.lego.framework.base.service.impl;
import com.lego.framework.auth.feign.AuthClient;
import com.lego.framework.base.service.IAuthCheckService;
import com.survey.lib.common.consts.RespConsts;
import com.survey.lib.common.vo.CurrentVo;
import com.survey.lib.common.vo.RespVO;
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
    public CurrentVo getData(String token,String deviceType) {

        RespVO<CurrentVo> currentVoRespVO = authClient.parseUserToken(token, deviceType);
        if(currentVoRespVO.getRetCode()== RespConsts.SUCCESS_RESULT_CODE){
            return currentVoRespVO.getInfo();
        }
        return null;
    }

}
