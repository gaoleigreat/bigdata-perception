package com.lego.perception.system.mapper;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.system.model.entity.TbSystem;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/3 10:48
 * @desc :
 */
@Repository
public interface SystemMapper extends Mapper<TbSystem> {

    PagedResult<TbSystem> selectPaged(RowBounds rowBounds);


    Integer insertSelective(TbSystem system);

    Integer insertSelectiveIgnore(TbSystem system);

    Integer updateByPrimaryKeySelective(TbSystem system);

    Integer updateByPrimaryKey(TbSystem system);

    Integer batchInsert(List<TbSystem> list);

    Integer batchUpdate(List<TbSystem> list);

    Integer upsert(TbSystem system);

    Integer upsertSelective(TbSystem system);

    List<TbSystem> query(TbSystem system);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);
}
