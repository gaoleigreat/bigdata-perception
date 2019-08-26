package com.lego.perception.system.service.impl;

import com.lego.framework.base.page.PagedResult;
import com.lego.framework.system.model.entity.Menu;
import com.lego.perception.system.mapper.MenuMapper;
import com.lego.perception.system.service.IMenuService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/8/26
 **/
@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public PagedResult<Menu> selectPaged(RowBounds rowBounds) {
        return menuMapper.selectPaged(rowBounds);
    }

    @Override
    public Menu selectByPrimaryKey(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByPrimaryKey(Long id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insert(Menu menu) {
        return menuMapper.insert(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertSelective(Menu menu) {
        return menuMapper.insertSelective(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertSelectiveIgnore(Menu menu) {
        return menuMapper.insertSelectiveIgnore(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateByPrimaryKeySelective(Menu menu) {
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateByPrimaryKey(Menu menu) {
        return menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer batchInsert(List<Menu> menuList) {
        return menuMapper.batchInsert(menuList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer batchUpdate(List<Menu> menuList) {
        return menuMapper.batchUpdate(menuList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer upsert(Menu menu) {
        return menuMapper.upsert(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer upsertSelective(Menu menu) {
        return menuMapper.upsertSelective(menu);
    }

    @Override
    public List<Menu> query(Menu menu) {
        return menuMapper.query(menu);
    }

    @Override
    public Long queryTotal() {
        return menuMapper.queryTotal();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteBatch(List<Long> list) {
        return menuMapper.deleteBatch(list);
    }
}
