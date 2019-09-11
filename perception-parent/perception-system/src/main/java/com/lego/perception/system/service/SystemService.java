package com.lego.perception.system.service;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.system.model.entity.TbSystem;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-03 02:38:11
 * @since jdk 1.8
 */
public interface SystemService {

    PagedResult<TbSystem> selectPaged(TbSystem tbSystem, Page page);

    TbSystem selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(TbSystem system);

    Integer insertSelective(TbSystem system);

    Integer insertSelectiveIgnore(TbSystem system);

    Integer updateByPrimaryKeySelective(TbSystem system);

    Integer updateByPrimaryKey(TbSystem system);

    Integer batchInsert(List<TbSystem> list);

    Integer batchUpdate(List<TbSystem> list);
    /**
     * 存在即更新
     *
     * @param system
     * @return
     */
    Integer upsert(TbSystem system);

    /**
     * 存在即更新，可选择具体属性
     *
     * @param system
     * @return
     */
    Integer upsertSelective(TbSystem system);

    List<TbSystem> query(TbSystem system);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}
