package com.lego.equipment.service.service;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.equipment.model.entity.ServiceComment;

import java.util.List;

/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-18 03:51:45
 * @since jdk 1.8
 */
public interface IServiceCommentService {


    PagedResult<ServiceComment> selectPaged(Page page, ServiceComment serviceComment);

    ServiceComment selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(ServiceComment serviceComment);

    Integer insertSelective(ServiceComment serviceComment);

    Integer insertSelectiveIgnore(ServiceComment serviceComment);

    Integer updateByPrimaryKeySelective(ServiceComment serviceComment);

    Integer updateByPrimaryKey(ServiceComment serviceComment);

    Integer batchInsert(List<ServiceComment> list);

    Integer batchUpdate(List<ServiceComment> list);

    /**
     * 存在即更新
     *
     * @param map
     * @return
     */
    Integer upsert(ServiceComment serviceComment);

    /**
     * 存在即更新，可选择具体属性
     *
     * @param map
     * @return
     */
    Integer upsertSelective(ServiceComment serviceComment);

    List<ServiceComment> query(ServiceComment serviceComment);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

    /*<AUTOGEN--END>*/

}
