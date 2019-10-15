package com.lego.framework.user.model.vo;

import com.framework.common.sdto.CurrentVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/10/15 10:25
 * @desc :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SsoLoginVo {
    private String idNumber;
    private String sessionId;
    private String result;
    private String message;
    private String ssoSupServerUrl;
    private String ssoClientUrl;
    private String tenantId;
    private Long time;
    private CurrentVo currentVo;
}
