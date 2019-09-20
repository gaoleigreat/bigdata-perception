package com.lego.perception.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.mybatis.annotation.DB;
import com.lego.framework.data.model.entity.Datasharedtable;
import com.lego.perception.data.mapper.DatasharedtableMapper;
import com.lego.perception.data.service.IDatasharedtableService;
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
    public List<Datasharedtable> queryMyList(Datasharedtable datasharedtable) {

        return datasharedtableMapper.query(datasharedtable);
    }


    @Override
    public Integer saveMyData(Datasharedtable datasharedtable) {
        return datasharedtableMapper.insert(datasharedtable);
    }
}



