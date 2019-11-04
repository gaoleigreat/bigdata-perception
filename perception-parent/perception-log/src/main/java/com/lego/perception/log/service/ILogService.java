package com.lego.perception.log.service;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.lego.framework.log.model.entity.Log;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /**
     * 导出日志信息
     * @param type
     * @param tag
     * @param startTime
     * @param endTime
     * @return
     */
    RespVO exportLog(String type, String tag, Long startTime, Long endTime, HttpServletResponse response);

    /**
     * @param type
     * @param tag
     * @param startTime
     * @param endTime
     * @return
     */
    List<Log> list(String type, String tag, Long startTime, Long endTime,Integer limit);
}
