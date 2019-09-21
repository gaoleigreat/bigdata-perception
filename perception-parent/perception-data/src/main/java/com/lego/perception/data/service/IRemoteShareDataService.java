package com.lego.perception.data.service;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.lego.framework.data.model.entity.RemoteSharedData;

import java.util.List;

/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-19 12:36:38
 * @since jdk 1.8
 */
public interface IRemoteShareDataService {


    /**
     * @param datasharedtable
     * @return
     */
    List<RemoteSharedData> queryRemoteList(RemoteSharedData datasharedtable);


    /**
     * @param datasharedtable
     * @return
     */
    Integer saveRemoteData(RemoteSharedData datasharedtable);


    /**
     * @param datasharedtables
     * @return
     */
    Integer saveRemoteDataBatch(List<RemoteSharedData> datasharedtables);


    /**
     * @param datasharedtable
     * @param page
     * @return
     */
    PagedResult<RemoteSharedData> queryRemoteListPaged(RemoteSharedData datasharedtable, Page page);


    /**
     * @param datasharedtable
     * @return
     */
    RespVO deleteRemoteData(RemoteSharedData datasharedtable);
}
