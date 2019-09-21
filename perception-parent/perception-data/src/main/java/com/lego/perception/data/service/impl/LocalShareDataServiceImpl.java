package com.lego.perception.data.service.impl;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.mybatis.utils.PageUtil;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.data.model.entity.LocalSharedData;
import com.lego.framework.data.model.entity.RemoteSharedData;
import com.lego.perception.data.config.HdfsProperties;
import com.lego.perception.data.config.MongoProperties;
import com.lego.perception.data.config.MysqlProperties;
import com.lego.perception.data.mapper.LocalSharedDataMapper;
import com.lego.perception.data.mapper.RemoteSharedDataMapper;
import com.lego.perception.data.service.ILocalShareDataService;
import com.lego.perception.data.service.IRemoteShareDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-19 12:36:38
 * @since jdk 1.8
 */
@Service
public class LocalShareDataServiceImpl implements ILocalShareDataService {

    @Autowired
    private LocalSharedDataMapper localSharedDataMapper;


    @Autowired
    private MysqlProperties mysqlProperties;

    @Autowired
    private MongoProperties mongoProperties;

    @Autowired
    private HdfsProperties hdfsProperties;



    @Override
    public List<LocalSharedData> queryLocalList(LocalSharedData datasharedtable) {
        return localSharedDataMapper.query(datasharedtable);
    }


    @Override
    public Integer saveLocalData(LocalSharedData datasharedtable) {
        return localSharedDataMapper.insert(datasharedtable);
    }

    @Override
    public Integer saveLocalDataBatch(List<LocalSharedData> datasharedtables) {
        return localSharedDataMapper.batchInsert(datasharedtables);
    }

    @Override
    public PagedResult<LocalSharedData> queryLocalListPaged(LocalSharedData datasharedtable, Page page) {
        return PageUtil.queryPaged(page, datasharedtable, localSharedDataMapper);
    }

    @Override
    public RespVO deleteLocalData(LocalSharedData datasharedtable) {
        Integer deleted = localSharedDataMapper.deletedByObj(datasharedtable);
        if (deleted > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }

}



