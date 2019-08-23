package com.lego.perception.system.service;

import com.lego.framework.system.model.vo.ScheduleJobVO;
import com.survey.lib.common.vo.RespVO;

public interface IScheduleJobService {

    /**
     * 新增定时job
     * @param scheduleJobVO
     * @return
     */
    RespVO addJob(ScheduleJobVO scheduleJobVO);

    /**
     * 暂停任务
     * @param scheduleJobVO
     * @return
     */
    RespVO pauseJob(ScheduleJobVO scheduleJobVO);

    /**
     * 恢复任务
     * @param scheduleJobVO
     * @return
     */
    RespVO resumeJob(ScheduleJobVO scheduleJobVO);

    /**
     * 删除任务
     * @param scheduleJobVO
     * @return
     */
    RespVO deleteJob(ScheduleJobVO scheduleJobVO);

    /**
     * 查询任务
     * @param scheduleJobVO
     * @return
     */
    RespVO findAllJob(ScheduleJobVO scheduleJobVO);
}
