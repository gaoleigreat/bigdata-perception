package com.lego.perception.data.mapper;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.data.model.entity.LocalSharedData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/1/19
 **/
@Repository
public interface LocalSharedDataMapper extends Mapper<LocalSharedData> {


    List<LocalSharedData> query(LocalSharedData datasharedtable);


    Integer upsertSelective(LocalSharedData datasharedtable);


    Integer upsert(LocalSharedData datasharedtable);

    Integer batchUpdate(List<LocalSharedData> datasharedtables);

    Integer batchInsert(List<LocalSharedData> datasharedtables);

    Long insertSelectiveIgnore(LocalSharedData datasharedtable);

    Long insertSelective(LocalSharedData datasharedtable);


    @Override
    int insert(LocalSharedData datasharedtable);


    Integer deletedByObj(LocalSharedData datasharedtable);

}
