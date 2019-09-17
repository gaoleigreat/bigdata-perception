package com.lego.perception.business.service;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.business.model.entity.Business;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-17 10:04:50
 * @since jdk 1.8
 */
public interface IBusinessService {

    PagedResult<Business> selectPaged(Business business, Page page);

    Business selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(Business business);

    Integer insertSelective(Business business);

    Integer insertSelectiveIgnore(Business business);

    Integer updateByPrimaryKeySelective(Business business);

    Integer updateByPrimaryKey(Business business);

    Integer batchInsert(List<Business> list);

    Integer batchUpdate(List<Business> list);

    /**
     * 存在即更新
     *
     * @param business
     * @return
     */
    Integer upsert(Business business);

    /**
     * 存在即更新，可选择具体属性
     *
     * @param business
     * @return
     */
    Integer upsertSelective(Business business);

    List<Business> query(Business business);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}
