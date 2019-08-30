package com.lego.perception.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.framework.system.model.entity.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataFileMapper extends BaseMapper<DataFile> {

    /**
     * 查询列表
     *
     * @param dataFile
     * @return
     */
    List<Dictionary> findList(DataFile dataFile);



    /**
     * 批量新增
     *
     * @param dataFiles
     * @return
     */
    Integer insertList(List<DataFile> dataFiles);


    /**
     * 批量更新
     *
     * @param dataFiles
     * @return
     */
    Integer updateList(List<DataFile> dataFiles);



    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    Integer deleteList(List<Long> ids);




    IPage<DataFile> queryList(IPage<DataFile> page, @Param("ew") Wrapper<DataFile> queryWrapper, @Param("dataFile") DataFile dataFile);


}
