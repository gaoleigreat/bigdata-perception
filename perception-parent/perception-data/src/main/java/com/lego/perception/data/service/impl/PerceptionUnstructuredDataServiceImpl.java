package com.lego.perception.data.service.impl;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.utils.PageUtil;
import com.lego.framework.data.model.entity.PerceptionUnstructuredData;
import com.lego.perception.data.mapper.PerceptionUnstructuredDataMapper;
import com.lego.perception.data.service.IPerceptionUnstructuredDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * @description IPerceptionUnstructuredData Service层
 * @author ¸ßÀÚ
 * @since jdk1.8
 */
@Service
public class PerceptionUnstructuredDataServiceImpl implements IPerceptionUnstructuredDataService {


     @Autowired
     private PerceptionUnstructuredDataMapper perceptionUnstructuredDataMapper;

    /**
     * 创建PerceptionUnstructuredData
     *
     * @param perceptionUnstructuredData
     * @return
     */
    @Override
    public Integer insert(PerceptionUnstructuredData perceptionUnstructuredData){
        if(perceptionUnstructuredData ==null){
            return 0;
        }
        return perceptionUnstructuredDataMapper.insert(perceptionUnstructuredData);
    }


    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteByPrimaryKey(Long id){
        if(id ==null){
            return 0;
        }
        Integer result = perceptionUnstructuredDataMapper.deleteById(id);
        return result;

    }

    /**
     * 修改PerceptionUnstructuredData
     *
     * @param perceptionUnstructuredData
     * @return
    */
    @Override
    public Integer updateByPrimaryKey(PerceptionUnstructuredData perceptionUnstructuredData){
        if(perceptionUnstructuredData ==null){
            return 0;
        }
        return perceptionUnstructuredDataMapper.updateById(perceptionUnstructuredData);
    }

    /**
    * 根据主键查询
    *
    * @param id
    * @return
    */
    @Override
    public PerceptionUnstructuredData selectByPrimaryKey(Long id){
        if(id ==null){
            return null;
        }
        PerceptionUnstructuredData perceptionUnstructuredData  = perceptionUnstructuredDataMapper.selectById(id);
        if (perceptionUnstructuredData == null){
            return null;
        }
        return perceptionUnstructuredData;
    }

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @param perceptionUnstructuredData PerceptionUnstructuredData
     * @return IPage<PerceptionUnstructuredData>
     */

    @Override
    public PagedResult<PerceptionUnstructuredData> selectPaged(PerceptionUnstructuredData perceptionUnstructuredData, Page page) {
        PagedResult pagedResult = PageUtil.queryPaged(page, perceptionUnstructuredData, perceptionUnstructuredDataMapper);
        return pagedResult;
    }

    /**
    * 批量插入
    * @param list List<PerceptionUnstructuredData
    * @return Integer
    */
    @Override
    public Integer batchInsert(List<PerceptionUnstructuredData> list){
        if (CollectionUtils.isEmpty(list)){
            return 0;
        }
        else{
            return perceptionUnstructuredDataMapper.batchInsert(list);
        }
    }

    /**
     * 批量更新
     * @param list List<PerceptionUnstructuredData>
     * @return Integer
     */
    @Override
    public Integer batchUpdate(List<PerceptionUnstructuredData> list){
        if (CollectionUtils.isEmpty(list)){
            return 0;
        }
        else{
            return perceptionUnstructuredDataMapper.batchUpdate(list);
        }
    }

    /**
     * 批量删除
     * @param list List<Long >
     * @return Integer
    */
    public Integer deleteBatchIds(List<Long> list){
        if (CollectionUtils.isEmpty(list)){
            return 0;
        } else{
            return perceptionUnstructuredDataMapper.deleteBatchIds(list);
        }
    }
    /**
     * 存在即更新
     * @param perceptionUnstructuredData PerceptionUnstructuredData
     * @return Integer
     */
    @Override
    public Integer upsert(PerceptionUnstructuredData perceptionUnstructuredData){

        if (perceptionUnstructuredData == null){
            return 0;
        }
        else{
            return perceptionUnstructuredDataMapper.upsert(perceptionUnstructuredData);
        }

    }

    /**
     * 存在即更新，可选择具体属性
     * @param perceptionUnstructuredData PerceptionUnstructuredData
     * @return Integer
     */
    @Override
    public Integer upsertSelective(PerceptionUnstructuredData perceptionUnstructuredData){
        if (perceptionUnstructuredData == null){
            return 0;
        }
        else{
            return perceptionUnstructuredDataMapper.upsert(perceptionUnstructuredData);
        }
    }

    /**
     * 条件查询
     * @param perceptionUnstructuredData PerceptionUnstructuredData
     * @return List<PerceptionUnstructuredData>
    */
    @Override
    public List<PerceptionUnstructuredData> query(PerceptionUnstructuredData perceptionUnstructuredData){
        if (perceptionUnstructuredData == null){
            return null;
        }
        else{
            return perceptionUnstructuredDataMapper.query(perceptionUnstructuredData);
        }
    }

    /**
     * 查询总数
     * @return Integer
     */
    @Override
    public Long queryTotalCount(){
        return perceptionUnstructuredDataMapper.queryTotalCount();
    }
}