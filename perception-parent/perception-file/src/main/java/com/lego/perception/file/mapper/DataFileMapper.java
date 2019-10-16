package com.lego.perception.file.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.system.model.entity.DataFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataFileMapper extends Mapper<DataFile> {

    /**
     * 查询列表
     *
     * @param dataFile
     * @return
     */
    List<DataFile> findList(DataFile dataFile);



    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    Integer insertList(List<DataFile> list);


    /**
     * 批量更新
     *
     * @param dataFiles
     * @return
     */
    Integer updateList(List<DataFile> list);



    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    Integer deleteList(List<Long> list);




    IPage<DataFile> queryList(IPage<DataFile> page, @Param("ew") Wrapper<DataFile> queryWrapper, @Param("dataFile") DataFile dataFile);


}
