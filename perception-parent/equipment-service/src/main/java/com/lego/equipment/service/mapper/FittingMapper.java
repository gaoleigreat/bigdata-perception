package com.lego.equipment.service.mapper;


import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.equipment.model.entity.Fitting;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 的dao层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-08 06:16:21
 * @since jdk1.8
 */
@Repository
public interface FittingMapper extends Mapper<Fitting> {

    Fitting selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    int insert(Fitting fitting);

    Integer insertSelective(Fitting fitting);

    Integer insertSelectiveIgnore(Fitting fitting);

    Integer updateByPrimaryKeySelective(Fitting fitting);

    Integer updateByPrimaryKey(Fitting fitting);

    Integer batchInsert(List<Fitting> list);

    Integer batchUpdate(List<Fitting> list);

    /**
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