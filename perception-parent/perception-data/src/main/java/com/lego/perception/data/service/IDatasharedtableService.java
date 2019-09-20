package com.lego.perception.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
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
    List<Datasharedtable> queryMyList(Datasharedtable datasharedtable);

    /**
     * @param datasharedtable
     * @return
     */
    List<Datasharedtable> queryShareList(Datasharedtable datasharedtable);


    /**
     * @param datasharedtable
     * @return
     */
    Integer saveShareData(Datasharedtable datasharedtable);


    /**
     * @param datasharedtables
     * @return
     */
    Integer saveShareDataBatch(List<Datasharedtable> datasharedtables);


    /**
     * @param datasharedtable
     * @return
     */
    Integer saveMyData(Datasharedtable datasharedtable);


    /**
     * @param datasharedtables
     * @return
     */
    Integer saveMyDataBatch(List<Datasharedtable> datasharedtables);

    /**
     * @param datasharedtable
     * @param page
     * @return
     */
    PagedResult<Datasharedtable> queryShareListPaged(Datasharedtable datasharedtable, Page page);

    /**
     * @param datasharedtable
     * @param page
     * @return
     */
    PagedResult<Datasharedtable> querymyListPaged(Datasharedtable datasharedtable, Page page);

    /**
     * @param datasharedtable
     * @return
     */
    RespVO deleteMyData(Datasharedtable datasharedtable);

    /**
     * @param datasharedtable
     * @return
     */
    RespVO deleteShareData(Datasharedtable datasharedtable);
}
