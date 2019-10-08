package com.lego.equipment.service.service;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.equipment.model.entity.Fitting;
import java.util.List;

/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-08 06:16:21
 * @since jdk 1.8
 */
public interface IFittingService {


    PagedResult<Fitting> selectPaged(Fitting fitting, Page page);

    Fitting selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(Fitting fitting);

    Integer insertSelective(Fitting fitting);

    Integer insertSelectiveIgnore(Fitting fitting);

    Integer updateByPrimaryKeySelective(Fitting fitting);

    Integer updateByPrimaryKey(Fitting fitting);

    Integer batchInsert(List<Fitting> list);

    Integer batchUpdate(List<Fitting> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(Fitting fitting);

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    Integer upsertSelective(Fitting fitting);

    List<Fitting> query(Fitting fitting);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}
