package com.lego.perception.data.service;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.lego.framework.data.model.entity.LocalSharedData;
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
public interface ILocalShareDataService {


    /**
     * @param datasharedtable
     * @return
     */
    List<LocalSharedData> queryLocalList(LocalSharedData datasharedtable);



    /**
     * @return
     */
    Integer saveLocalData(LocalSharedData localSharedData);


    /**
     * @param datasharedtables
     * @return
     */
    Integer saveLocalDataBatch(List<LocalSharedData> datasharedtables);


    /**
     * @param datasharedtable
     * @param page
     * @return
     */
    PagedResult<LocalSharedData> queryLocalListPaged(LocalSharedData datasharedtable, Page page);

    /**
     * @param datasharedtable
     * @return
     */
    RespVO deleteLocalData(LocalSharedData datasharedtable);
}
