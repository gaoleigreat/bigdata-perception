package com.lego.perception.data.service.impl;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.mybatis.annotation.DB;
import com.framework.mybatis.utils.PageUtil;
import com.lego.framework.data.model.entity.RemoteSharedData;
import com.lego.perception.data.mapper.RemoteSharedDataMapper;
import com.lego.perception.data.service.IRemoteShareDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-19 12:36:38
 * @since jdk 1.8
 */
@Service
public class RemoteShareDataServiceImpl implements IRemoteShareDataService {


    @Autowired
    private RemoteSharedDataMapper remoteSharedDataMapper;


    @Override
    @DB(value = "share")
    public List<RemoteSharedData> queryRemoteList(RemoteSharedData datasharedtable) {
        return remoteSharedDataMapper.query(datasharedtable);
    }

    @Override
    @DB(value = "share")
    public Integer saveRemoteData(RemoteSharedData datasharedtable) {
        return remoteSharedDataMapper.insert(datasharedtable);
    }

    @Override
    @DB(value = "share")
    public Integer saveRemoteDataBatch(List<RemoteSharedData> datasharedtables) {
        return remoteSharedDataMapper.batchInsert(datasharedtables);
    }


    @Override
    @DB(value = "share")
    public PagedResult<RemoteSharedData> queryRemoteListPaged(RemoteSharedData datasharedtable, Page page) {
        return PageUtil.queryPaged(page, datasharedtable, remoteSharedDataMapper);
    }


    @Override
    @DB(value = "share")
    public RespVO deleteRemoteData(RemoteSharedData datasharedtable) {
        Integer deleted = remoteSharedDataMapper.deletedByObj(datasharedtable);
        if (deleted > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }

}



