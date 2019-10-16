package com.lego.equipment.service.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.equipment.model.entity.EquipmentService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentServiceMapper extends Mapper<EquipmentService> {
    /*<AUTOGEN--BEGIN>*/


    EquipmentService selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);



    Integer insertSelective(EquipmentService tplEquipmentService);

    Integer insertSelectiveIgnore(EquipmentService tplEquipmentService);

    Integer updateByPrimaryKeySelective(EquipmentService tplEquipmentService);

    Integer updateByPrimaryKey(EquipmentService tplEquipmentService);

    Integer batchInsert(List<EquipmentService> list);

    Integer batchUpdate(List<EquipmentService> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(EquipmentService tplEquipmentService);

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    Integer upsertSelective(EquipmentService tplEquipmentService);

    List<EquipmentService> query(EquipmentService tplEquipmentService);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

    /**
     * @param wrapper
     * @param page
     * @return
     */
    IPage<EquipmentService> queryList(QueryWrapper wrapper, IPage page);
}
