package com.lego.perception.system.service.impl;
import com.framework.common.page.PagedResult;
import com.lego.framework.system.model.entity.TbSystem;
import com.lego.perception.system.mapper.SystemMapper;
import com.lego.perception.system.service.SystemService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-03 02:38:11
 * @since jdk 1.8
 */
@Service("systemService")
public class SystemServiceImpl implements SystemService {
    /*<AUTOGEN--BEGIN>*/

    @Autowired
    public SystemMapper systemMapper;


    @Override
    public PagedResult<TbSystem> selectPaged(RowBounds rowBounds) {
        return systemMapper.selectPaged(rowBounds);
    }

    @Override
    public TbSystem selectByPrimaryKey(Long id) {
        return systemMapper.selectById(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return systemMapper.deleteById(id);
    }

    @Override
    public Integer insert(TbSystem system) {
        return systemMapper.insert(system);
    }

    @Override
    public Integer insertSelective(TbSystem system) {
        return systemMapper.insertSelective(system);
    }

    @Override
    public Integer insertSelectiveIgnore(TbSystem system) {
        return systemMapper.insertSelectiveIgnore(system);
    }

    @Override
    public Integer updateByPrimaryKeySelective(TbSystem system) {
        return systemMapper.updateByPrimaryKeySelective(system);
    }

    @Override
    public Integer updateByPrimaryKey(TbSystem system) {
        return systemMapper.updateByPrimaryKey(system);
    }

    @Override
    public Integer batchInsert(List<TbSystem> list) {
        return systemMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<TbSystem> list) {
        return systemMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @param system
     * @return
     */
    @Override
    public Integer upsert(TbSystem system) {
        return systemMapper.upsert(system);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @param system
     * @return
     */
    @Override
    public Integer upsertSelective(TbSystem system) {
        return systemMapper.upsertSelective(system);
    }

    @Override
    public List<TbSystem> query(TbSystem system) {
        return systemMapper.query(system);
    }

    @Override
    public Long queryTotal() {
        return systemMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return systemMapper.deleteBatch(list);
    }
}
