package com.lego.perception.system.mapper;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.system.model.entity.Menu;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/8/26
 **/
@Repository
public interface MenuMapper extends Mapper<Menu> {


    PagedResult<Menu> selectPaged(RowBounds rowBounds);

    Menu selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);


    Integer insertSelective(Menu menu);

    Integer insertSelectiveIgnore(Menu menu);

    Integer updateByPrimaryKeySelective(Menu menu);

    Integer updateByPrimaryKey(Menu menu);

    Integer batchInsert(List<Menu> menuList);

    Integer batchUpdate(List<Menu> menuList);

    /**
     * 存在即更新
     *
     * @param menu
     * @return
     */
    Integer upsert(Menu menu);

    /**
     * 存在即更新，可选择具体属性
     *
     * @param menu
     * @return
     */
    Integer upsertSelective(Menu menu);

    List<Menu> query(Menu menu);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}
