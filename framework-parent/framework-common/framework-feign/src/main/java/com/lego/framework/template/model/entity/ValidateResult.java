package com.lego.framework.template.model.entity;

import java.io.Serializable;

public class ValidateResult implements Serializable {


    private static final long serialVersionUID = 5478489273776675684L;

    private Boolean result;

    private String msg;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
