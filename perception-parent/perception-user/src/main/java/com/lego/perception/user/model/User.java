package com.lego.perception.user.model;


import lombok.Data;

import java.io.Serializable;


@Data
public class User implements Serializable
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 4074856906510638095L;

    /**
     * 身份证号码constant
     */
    private String idNumber;

    /**
     * 帐号
     */
    private String userName;
}
