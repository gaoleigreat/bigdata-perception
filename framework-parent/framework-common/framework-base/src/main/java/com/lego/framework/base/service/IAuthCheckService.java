package com.lego.framework.base.service;

import com.framework.common.sdto.CurrentVo;

public interface IAuthCheckService {

    /**
     * 获取缓存数据
     * @param token
     * @return
     */
    CurrentVo getData(String token);

}
