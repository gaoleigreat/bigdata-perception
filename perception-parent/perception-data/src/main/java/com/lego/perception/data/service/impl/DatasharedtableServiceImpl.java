package com.lego.perception.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.mybatis.annotation.DB;
import com.framework.mybatis.utils.PageUtil;
import com.lego.framework.data.model.entity.Datasharedtable;
import com.lego.perception.data.mapper.DatasharedtableMapper;
import com.lego.perception.data.service.IDatasharedtableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-19 12:36:38
 * @since jdk 1.8
 */
@Service
public class DatasharedtableServiceImpl extends ServiceImpl<DatasharedtableMapper, Datasharedtable> implements IDatasharedtableService {

    @Autowired
    private DatasharedtableMapper datasharedtableMapper;


    @Override
    @DB(value = "share")
    public List<Datasharedtable> queryShareList(Datasharedtable datasharedtable) {
        return datasharedtableMapper.query(datasharedtable);
    }

    @Override
    @DB(value = "share")
    public Integer saveShareData(Datasharedtable datasharedtable) {
        return datasharedtableMapper.insert(datasharedtable);
    }

    @Override
    @DB(value = "share")
    public Integer saveShareDataBatch(List<Datasharedtable> datasharedtables) {
        return datasharedtableMapper.batchInsert(datasharedtables);
    }


    @Override
    public List<Datasharedtable> queryMyList(Datasharedtable datasharedtable) {
        return datasharedtableMapper.query(datasharedtable);
    }


    @Override
    public Integer saveMyData(Datasharedtable datasharedtable) {
        return datasharedtableMapper.insert(datasharedtable);
    }

    @Override
    public Integer saveMyDataBatch(List<Datasharedtable> datasharedtables) {
        return datasharedtableMapper.batchInsert(datasharedtables);
    }

    @Override
    @DB(value = "share")
    public PagedResult<Datasharedtable> queryShareListPaged(Datasharedtable datasharedtable, Page page) {
        return PageUtil.queryPaged(page, datasharedtable, datasharedtableMapper);
    }

    @Override
    public PagedResult<Datasharedtable> querymyListPaged(Datasharedtable datasharedtable, Page page) {
        return PageUtil.queryPaged(page, datasharedtable, datasharedtableMapper);
    }

    @Override
    public RespVO deleteMyData(Datasharedtable datasharedtable) {
        Integer deleted = datasharedtableMapper.deletedByObj(datasharedtable);
        if (deleted > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }

    @Override
    @DB(value = "share")
    public RespVO deleteShareData(Datasharedtable datasharedtable) {
        Integer deleted = datasharedtableMapper.deletedByObj(datasharedtable);
        if (deleted > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


}



