package com.lego.perception.data.mapper;

import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.data.model.entity.Datasharedtable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/1/19
 **/
@Repository
public interface DatasharedtableMapper extends Mapper<Datasharedtable> {


    List<Datasharedtable> query(Datasharedtable datasharedtable);


    Integer upsertSelective(Datasharedtable datasharedtable);


    Integer upsert(Datasharedtable datasharedtable);

    Integer batchUpdate(List<Datasharedtable> datasharedtables);

    Integer batchInsert(List<Datasharedtable> datasharedtables);

    Long insertSelectiveIgnore(Datasharedtable datasharedtable);

    Long insertSelective (Datasharedtable datasharedtable);

    int insert (Datasharedtable datasharedtable);
}
