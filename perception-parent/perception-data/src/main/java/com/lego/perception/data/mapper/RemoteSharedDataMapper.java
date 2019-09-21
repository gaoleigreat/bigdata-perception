package com.lego.perception.data.mapper;
import com.framework.mybatis.annotation.DB;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.data.model.entity.RemoteSharedData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/1/19
 **/
@Repository
public interface RemoteSharedDataMapper extends Mapper<RemoteSharedData> {


    /**
     * @param remoteSharedData
     * @return
     */
    @DB(value = "share")
    List<RemoteSharedData> query(RemoteSharedData remoteSharedData);


    /**
     * @param remoteSharedData
     * @return
     */
    @DB(value = "share")
    Integer upsertSelective(RemoteSharedData remoteSharedData);

    /**
     * @param remoteSharedData
     * @return
     */
    @DB(value = "share")
    Integer upsert(RemoteSharedData remoteSharedData);

    /**
     * @param sharedDataList
     * @return
     */
    @DB(value = "share")
    Integer batchUpdate(List<RemoteSharedData> sharedDataList);

    /**
     * @param sharedDataList
     * @return
     */
    @DB(value = "share")
    Integer batchInsert(List<RemoteSharedData> sharedDataList);

    /**
     * @param remoteSharedData
     * @return
     */
    @DB(value = "share")
    Long insertSelectiveIgnore(RemoteSharedData remoteSharedData);

    /**
     * @param remoteSharedData
     * @return
     */
    @DB(value = "share")
    Long insertSelective(RemoteSharedData remoteSharedData);


    @DB(value = "share")
    int insert(RemoteSharedData remoteSharedData);

    /**
     * @param remoteSharedData
     * @return
     */
    @DB(value = "share")
    Integer deletedByObj(RemoteSharedData remoteSharedData);

}
