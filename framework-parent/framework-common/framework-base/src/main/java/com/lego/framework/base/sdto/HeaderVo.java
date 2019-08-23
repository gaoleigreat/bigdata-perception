package com.lego.framework.base.sdto;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yanglf
 * @description
 * @since 2018/12/27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeaderVo {
    private String token;
    private String time;
    private String deviceType;
    private String osVersion;
    private String sn;
    private String fromName;

    private String userId;
    private String userName;

    public String toJsonObject(){
        return JSONObject.toJSONString(this);
    }
}
