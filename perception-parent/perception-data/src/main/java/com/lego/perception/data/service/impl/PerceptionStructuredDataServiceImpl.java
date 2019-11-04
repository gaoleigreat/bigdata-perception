package com.lego.perception.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.framework.data.model.entity.PerceptionStructuredData;
import com.lego.perception.data.mapper.PerceptionStructuredDataMapper;
import com.lego.perception.data.service.IPerceptionStructuredDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @author ¸ßÀÚ
 * @description IPerceptionStructuredData Service层
 * @since jdk1.8
 */
@Service
public class PerceptionStructuredDataServiceImpl extends ServiceImpl<PerceptionStructuredDataMapper, PerceptionStructuredData> implements IPerceptionStructuredDataService {


    @Autowired
    private PerceptionStructuredDataMapper perceptionStructuredDataMapper;

    /**
     * 创建PerceptionStructuredData
     *
     * @param perceptionStructuredData
     * @return
     */
    @Override
    public Integer insert(PerceptionStructuredData perceptionStructuredData) {
        if (perceptionStructuredData == null) {
            return 0;
        }
        return perceptionStructuredDataMapper.insert(perceptionStructuredData);
    }


    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteByPrimaryKey(Long id) {
        if (id == null) {
            return 0;
        }
        Integer result = perceptionStructuredDataMapper.deleteById(id);
        return result;

    }

    /**
     * 修改PerceptionStructuredData
     *
     * @param perceptionStructuredData
     * @return
     */
    @Override
    public Integer updateByPrimaryKey(PerceptionStructuredData perceptionStructuredData) {
        if (perceptionStructuredData == null) {
            return 0;
        }
        return perceptionStructuredDataMapper.updateById(perceptionStructuredData);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    @Override
    public PerceptionStructuredData selectByPrimaryKey(Long id) {
        if (id == null) {
            return null;
        }
        PerceptionStructuredData perceptionStructuredData = perceptionStructuredDataMapper.selectById(id);
        if (perceptionStructuredData == null) {
            return null;
        }
        return perceptionStructuredData;
    }

    @Override
    public PagedResult<PerceptionStructuredData> selectPaged(PerceptionStructuredData perceptionStructuredData, Page page) {
        return PageUtil.queryPaged(page, perceptionStructuredData, perceptionStructuredDataMapper);
    }

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @param perceptionStructuredData PerceptionStructuredData
     * @return IPage<PerceptionStructuredData>
     */


    /**
     * 批量插入
     *
     * @param list List<PerceptionStructuredData
     * @return Integer
     */
    @Override
    public Integer batchInsert(List<PerceptionStructuredData> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        } else {
            return perceptionStructuredDataMapper.batchInsert(list);
        }
    }

    /**
     * 批量更新
     *
     * @param list List<PerceptionStructuredData>
     * @return Integer
     */
    @Override
    public Integer batchUpdate(List<PerceptionStructuredData> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        } else {
            return perceptionStructuredDataMapper.batchUpdate(list);
        }
    }

    /**
     * 批量删除
     *
     * @param list List<Long >
     * @return Integer
     */
    @Override
    public Integer deleteBatchIds(List<Long> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        } else {
            return perceptionStructuredDataMapper.deleteBatchIds(list);
        }
    }

    /**
     * 存在即更新
     *
     * @param perceptionStructuredData PerceptionStructuredData
     * @return Integer
     */
    @Override
    public Integer upsert(PerceptionStructuredData perceptionStructuredData) {

        if (perceptionStructuredData == null) {
            return 0;
        } else {
            return perceptionStructuredDataMapper.upsert(perceptionStructuredData);
        }

    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @param perceptionStructuredData PerceptionStructuredData
     * @return Integer
     */
    @Override
    public Integer upsertSelective(PerceptionStructuredData perceptionStructuredData) {
        if (perceptionStructuredData == null) {
            return 0;
        } else {
            return perceptionStructuredDataMapper.upsert(perceptionStructuredData);
        }
    }

    /**
     * 条件查询
     *
     * @param perceptionStructuredData PerceptionStructuredData
     * @return List<PerceptionStructuredData>
     */
    @Override
    public List<PerceptionStructuredData> query(PerceptionStructuredData perceptionStructuredData) {
        if (perceptionStructuredData == null) {
            return null;
        } else {
            return perceptionStructuredDataMapper.query(perceptionStructuredData);
        }
    }

    /**
     * 查询总数
     *
     * @return Integer
     */
    @Override
    public Long queryTotalCount() {
        return perceptionStructuredDataMapper.queryTotalCount();
    }

    @Override
    public List<PerceptionStructuredData> selectDataByBatchNum(List<String> batchNums,
                                                               String tags) {
        QueryWrapper<PerceptionStructuredData> wrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(batchNums)) {
            wrapper.in("batch_num", batchNums);
        }
        if (!StringUtils.isEmpty(tags)) {
            String[] split = tags.split(",");
            if (split.length > 0) {
                wrapper.in("tags", split);
            }
        }
        wrapper.eq("delete_flag",0);
        wrapper.eq("publish_flag",0);
        return perceptionStructuredDataMapper.selectList(wrapper);
    }
}