package com.lego.perception.business.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.business.model.entity.BusinessTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/3 18:22
 * @desc :
 */
@Repository
public interface CrudMapper extends Mapper<BusinessTable> {


    /**
     * 创建业务数据表
     *
     * @param tableName
     * @param column
     */
    Integer createBusinessTable(@Param(value = "tableName") String tableName,
                                @Param(value = "column") String column);


    /**
     * 查询表是否存在
     *
     * @param tableName
     * @return
     */
    Integer existTable(@Param(value = "tableName") String tableName);


    /**
     * 查询业务数据
     *
     * @param tableName
     * @param wrapper
     * @return
     */
    List<Map<String, Object>> queryBusinessData(@Param(value = "tableName") String tableName,
                                                @Param(value = "ew") QueryWrapper wrapper);


    /**
     * 分页查询业务数据
     *
     * @param tableName
     * @param wrapper
     * @param iPage
     * @return
     */
    IPage<Map<String, Object>> queryBusinessData(IPage iPage, @Param(value = "tableName") String tableName,
                                                 @Param(value = "ew") QueryWrapper wrapper);


    /**
     * 新增数据
     *
     * @return
     */
    Integer insertBusinessData(BusinessTable businessTable);


    /**
     * 根据id修改业务数据
     *
     * @param businessTable
     * @return
     */
    Integer updateByID(BusinessTable businessTable);


    /**
     * 删除业务数据
     *
     * @param businessTable
     * @return
     */
    Integer delBusinessData(BusinessTable businessTable);


    /**
     * @param tableName
     * @param equipmentCode
     * @return
     */
    Map<String, Object> queryByCode(@Param(value = "tableName") String tableName,
                                    @Param(value = "equipmentCode") String equipmentCode);
}
