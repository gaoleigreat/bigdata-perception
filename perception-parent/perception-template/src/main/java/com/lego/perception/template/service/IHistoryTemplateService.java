package com.lego.perception.template.service;
import com.framework.common.sdto.RespVO;

/**
 * 模板历史纪录
 * weihao 2019-03-06
 */
public interface IHistoryTemplateService<T> {

    /**
     * 查询
     * @param code
     * @param tag
     * @return
     */
    T find(String code, String tag);

    /**
     * 新增
     * @param t
     * @return
     */
    RespVO insert(T t);
}
