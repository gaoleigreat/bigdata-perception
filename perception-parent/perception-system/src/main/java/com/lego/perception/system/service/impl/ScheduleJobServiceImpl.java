package com.lego.perception.system.service.impl;

import com.lego.perception.system.model.ScheduleJob;
import com.lego.perception.system.service.IScheduleJobService;
import com.lego.framework.system.model.vo.ScheduleJobVO;
import com.survey.lib.common.vo.RespVO;
import com.survey.lib.common.vo.RespVOBuilder;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ScheduleJobServiceImpl implements IScheduleJobService {


    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public RespVO addJob(ScheduleJobVO scheduleJobVO) {
        try {
            //定时器
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            //任务标识
            JobKey jobKey = JobKey.jobKey(scheduleJobVO.getGroupName(), scheduleJobVO.getJobName());

            //任务存在，返回
            boolean exists = scheduler.checkExists(jobKey);
            if (exists) {
                return RespVOBuilder.failure("任务已存在");
            }
            //创建任务
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(scheduleJobVO.getGroupName(), scheduleJobVO.getJobName()).withDescription(scheduleJobVO.getDescription()).build();
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            jobDataMap.put("url", scheduleJobVO.getUrl());
            jobDataMap.put("groupName", scheduleJobVO.getGroupName());
            jobDataMap.put("jobName", scheduleJobVO.getJobName());

            //定时表达式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJobVO.getCron());

            //创建触发器,1秒钟后触发器开始触发
            CronTrigger trigger = TriggerBuilder.newTrigger().startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND)).withIdentity("trigger_" + scheduleJobVO.getGroupName(), "trigger_" + scheduleJobVO.getJobName()).withSchedule(scheduleBuilder).build();

            //部署定时任务
            scheduler.scheduleJob(jobDetail, trigger);

            //定时引擎没有启动，启动定时引擎
            if (scheduler.isShutdown()) {
                scheduler.start();
            }

        } catch (SchedulerException e) {

            log.error("", e);
            return RespVOBuilder.failure(e.getMessage());
        }
        return RespVOBuilder.success();
    }

    @Override
    public RespVO pauseJob(ScheduleJobVO scheduleJobVO) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey("trigger_" + scheduleJobVO.getGroupName(), "trigger_" + scheduleJobVO.getJobName());

            scheduler.pauseTrigger(triggerKey);
            log.info("=========================pause job:" + scheduleJobVO.getJobName() + "-----" + scheduleJobVO.getGroupName() + " success========================");
        } catch (SchedulerException e) {
            log.error("", e);
            return RespVOBuilder.failure(e.getMessage());
        }
        return RespVOBuilder.success();
    }

    @Override
    public RespVO resumeJob(ScheduleJobVO scheduleJobVO) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey("trigger_" + scheduleJobVO.getGroupName(), "trigger_" + scheduleJobVO.getJobName());
            scheduler.resumeTrigger(triggerKey);
            log.error("=========================resume job:" + scheduleJobVO.getJobName() + "-----" + scheduleJobVO.getGroupName() + " success========================");
        } catch (SchedulerException e) {
            log.error("", e);
            return RespVOBuilder.failure(e.getMessage());
        }
        return RespVOBuilder.success();
    }

    @Override
    public RespVO deleteJob(ScheduleJobVO scheduleJobVO) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(scheduleJobVO.getGroupName(), scheduleJobVO.getJobName());
            scheduler.deleteJob(jobKey);
            log.error("=========================delete job:" + scheduleJobVO.getGroupName() + "-----" + scheduleJobVO.getJobName() + " success========================");
        } catch (SchedulerException e) {
            log.error("", e);
            return RespVOBuilder.failure(e.getMessage());
        }
        return RespVOBuilder.success();
    }

    @Override
    public RespVO findAllJob(ScheduleJobVO scheduleJobVO) {
        List<ScheduleJobVO> result = new ArrayList<>();
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            Set<JobKey> jobKeySet = scheduler.getJobKeys(GroupMatcher.anyGroup());

            Iterator<JobKey> it = jobKeySet.iterator();
            while (it.hasNext()) {
                JobKey jobKey = it.next();
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                JobDataMap jobDataMap = jobDetail.getJobDataMap();
                String groupName = (String) jobDataMap.get("groupName");
                String jobName = (String) jobDataMap.get("jobName");
                String cron = (String) jobDataMap.get("cron");
                String url = (String) jobDataMap.get("url");

                if (!StringUtils.isEmpty(scheduleJobVO.getGroupName()) && groupName.indexOf(scheduleJobVO.getGroupName()) < 0) {
                    continue;
                }

                if (!StringUtils.isEmpty(scheduleJobVO.getJobName()) && jobName.indexOf(scheduleJobVO.getJobName()) < 0) {
                    continue;
                }

                ScheduleJobVO job = new ScheduleJobVO();
                job.setCron(cron);
                job.setDescription(jobDetail.getDescription());
                job.setGroupName(groupName);
                job.setJobName(jobName);
                job.setUrl(url);
                result.add(job);
            }
        } catch (SchedulerException e) {
            log.error("", e);
            return RespVOBuilder.failure(e.getMessage());
        }
        return RespVOBuilder.success(result);
    }
}
