package com.lego.perception.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lego.framework.data.model.entity.Datasharedtable;

import java.util.List;

/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-19 12:36:38
 * @since jdk 1.8
 */
public interface IDatasharedtableService extends IService<Datasharedtable> {


    /**
     * @param datasharedtable
     * @return
     */
    List<Datasharedtable> queryList(Datasharedtable datasharedtable);
}
