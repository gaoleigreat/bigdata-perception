package com.lego.framework.zuul.config;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * @author yanglf
 * @description
 * @since 2019/8/9
 **/
public class SimpleValueFilter implements ValueFilter {

    @Override
    public Object process(Object object, String name, Object value) {
        if (value instanceof String) {
            value = ((String) value).trim();
        }
        return value;
    }
}
