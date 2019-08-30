package com.lego.perception.template.mapper;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.template.model.entity.EnumerationItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnumerationItemMapper extends Mapper<EnumerationItem> {

    /**
     * 查询列表
     * @param item
     * @return
     */
    List<EnumerationItem> findList(EnumerationItem item);


    /** 获取枚举  item
     * @param item
     * @return
     */
    EnumerationItem findItem(EnumerationItem item);



    /**
     * 新增列表
     * @param items
     * @return
     */
    Integer insertList(List<EnumerationItem> items);

    /**
     * 删除列表
     * @param ids
     * @return
     */
    Integer deleteList(List<Long> ids);

    /**
     * 根据enumid删除
     * @param enumId
     * @return
     */
    Integer deleteByEnumId(Long enumId);
}
