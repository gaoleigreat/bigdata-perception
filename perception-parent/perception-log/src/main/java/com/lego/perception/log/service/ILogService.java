package com.lego.perception.log.service;
import com.lego.framework.base.page.PagedResult;
import com.lego.framework.base.sdto.RespVO;
import com.lego.framework.log.model.entity.Log;
import com.lego.framework.log.model.vo.LogVo;

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
    RespVO<PagedResult<LogVo>>   list(int pageIndex, int pageSize);


    /**
     * @param id
     * @return
     */
    Log findLastLoginLogByUserId(String id);
}
