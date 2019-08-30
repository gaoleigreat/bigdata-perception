package com.lego.perception.template.service;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.lego.framework.template.model.entity.Enumeration;

import java.util.List;

/**
 * 枚举
 * weihao 2019-02-16
 */
public interface IEnumerationService {

    /**
     * 分页查询
     * @param enumeration
     * @param page
     * @return
     */
    PagedResult<Enumeration> findPagedList(Enumeration enumeration, Page page);

    /**
     * 查询列表
     * @param enumeration
     * @return
     */
    List<Enumeration> findList(Enumeration enumeration);

    /**
     * 查询详情
     * @param enumeration
     * @return
     */
    Enumeration find(Enumeration enumeration);

    /**
     * 新增
     * @param enumeration
     * @return
     */
    RespVO insert(Enumeration enumeration);

    /**
     * 更新
     * @param enumeration
     * @return
     */
    RespVO update(Enumeration enumeration);

    /**
     * 删除
     * @param id
     * @return
     */
    RespVO delete(Long id);
}
