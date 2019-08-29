package com.lego.perception.log.service;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.lego.framework.log.model.entity.Log;
/**
 * @author yanglf
 * @description
 * @since 2018/12/28
 **/
public interface ILogService {

    /**
     * 新增日志
     * @param log
     * @return
     */
    RespVO add(Log log);


    /**
     * 获取日志列表
     * @param pageIndex
     * @param pageSize
     * @return
     */
    RespVO<PagedResult<Log>>   list(int pageIndex, int pageSize,String type, String tag,Long startTime,Long endTime);


    /**
     * @param id
     * @return
     */
    Log findLastLoginLogByUserId(String id);
}
