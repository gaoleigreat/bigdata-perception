package com.lego.equipment.service.mapper;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.equipment.model.entity.ServiceComment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 的dao层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-18 03:51:45
 * @since jdk1.8
 */
@Repository
public interface ServiceCommentMapper extends Mapper<ServiceComment> {


    ServiceComment selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    @Override
    int insert(ServiceComment serviceComment);

    Integer insertSelective(ServiceComment serviceComment);

    Integer insertSelectiveIgnore(ServiceComment serviceComment);

    Integer updateByPrimaryKeySelective(ServiceComment serviceComment);

    Integer updateByPrimaryKey(ServiceComment serviceComment);

    Integer batchInsert(List<ServiceComment> list);

    Integer batchUpdate(List<ServiceComment> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(ServiceComment serviceComment);

    /**
     * 存在即更新，可选择具体属性
     * @return
     */
    Integer upsertSelective(ServiceComment serviceComment);

    List<ServiceComment> query(ServiceComment serviceComment);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}