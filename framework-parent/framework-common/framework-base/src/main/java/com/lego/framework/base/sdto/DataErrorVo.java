package com.lego.framework.base.sdto;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yanglf
 * @description
 * @since 2019/1/18
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataErrorVo {
    private Long id;
    private String desc;
    private int type;

    public  String toJsonObject(){
        return JSONObject.toJSONString(this);
    }


}
