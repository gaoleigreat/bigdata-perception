package com.lego.perception.data.mapper;
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
    List<RemoteSharedData> query(RemoteSharedData remoteSharedData);


    /**
     * @param remoteSharedData
     * @return
     */
    Integer upsertSelective(RemoteSharedData remoteSharedData);

    /**
     * @param remoteSharedData
     * @return
     */
    Integer upsert(RemoteSharedData remoteSharedData);

    /**
     * @param sharedDataList
     * @return
     */
    Integer batchUpdate(List<RemoteSharedData> sharedDataList);

    /**
     * @param sharedDataList
     * @return
     */
    Integer batchInsert(List<RemoteSharedData> sharedDataList);

    /**
     * @param remoteSharedData
     * @return
     */
    Long insertSelectiveIgnore(RemoteSharedData remoteSharedData);

    /**
     * @param remoteSharedData
     * @return
     */
    Long insertSelective(RemoteSharedData remoteSharedData);


    @Override
    int insert(RemoteSharedData remoteSharedData);

    /**
     * @param remoteSharedData
     * @return
     */
    Integer deletedByObj(RemoteSharedData remoteSharedData);

}
