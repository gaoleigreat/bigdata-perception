package com.lego.perception.template.service;
import com.framework.common.sdto.RespVO;
import com.lego.framework.template.model.entity.EnumerationItem;

import java.util.List;

/**
 * 枚举项
 * weihao 2019-02-27
 */
public interface IEnumerationItemService {

    /**
     * 查询列表
     * @param item
     * @return
     */
    List<EnumerationItem> findList(EnumerationItem item);


    /**
     * 获取枚举item
     * @param item
     * @return
     */
    EnumerationItem   finItem(EnumerationItem item);



    /**
     * 批量新增
     * @param lst
     * @return
     */
    RespVO insertList(List<EnumerationItem> lst);

    /**
     * 批量删除
     * @param lst
     * @return
     */
    RespVO deleteList(List<Long> lst);

    /**
     * 根据枚举id删除枚举项
     * @param enumId
     * @return
     */
    RespVO deleteByEnumId(Long enumId);

}
