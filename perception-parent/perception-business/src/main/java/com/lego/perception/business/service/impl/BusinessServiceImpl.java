package com.lego.perception.business.service.impl;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.framework.business.model.entity.Business;
import com.lego.perception.business.mapper.BusinessMapper;
import com.lego.perception.business.service.IBusinessService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-17 10:04:50
 * @since jdk 1.8
 */
@Service
public class BusinessServiceImpl implements IBusinessService {

    @Autowired
    public BusinessMapper businessMapper;


    @Override
    public PagedResult<Business> selectPaged(Business business, Page page) {
        return PageUtil.queryPaged(page,business,businessMapper);
    }

    @Override
    public Business selectByPrimaryKey(Long id) {
        return businessMapper.selectById(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return businessMapper.deleteById(id);
    }

    @Override
    public Integer insert(Business business) {
        return businessMapper.insert(business);
    }

    @Override
    public Integer insertSelective(Business business) {
        return businessMapper.insertSelective(business);
    }

    @Override
    public Integer insertSelectiveIgnore(Business business) {
        return businessMapper.insertSelectiveIgnore(business);
    }

    @Override
    public Integer updateByPrimaryKeySelective(Business business) {
        return businessMapper.updateByPrimaryKeySelective(business);
    }

    @Override
    public Integer updateByPrimaryKey(Business business) {
        return businessMapper.updateByPrimaryKey(business);
    }

    @Override
    public Integer batchInsert(List<Business> list) {
        return businessMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<Business> list) {
        return businessMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @param business
     * @return
     */
    @Override
    public Integer upsert(Business business) {
        return businessMapper.upsert(business);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @param business
     * @return
     */
    @Override
    public Integer upsertSelective(Business business) {
        return businessMapper.upsertSelective(business);
    }

    @Override
    public List<Business> query(Business business) {
        return businessMapper.query(business);
    }

    @Override
    public Long queryTotal() {
        return businessMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return businessMapper.deleteBatch(list);
    }


}
