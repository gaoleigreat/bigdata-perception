package com.lego.perception.log.service.impl;
import com.framework.common.consts.RespConsts;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.excel.ExcelService;
import com.lego.framework.base.utils.DateUtils;
import com.lego.framework.base.utils.UuidUtils;
import com.lego.framework.log.model.entity.Log;
import com.lego.framework.log.model.vo.LogExportVo;
import com.lego.framework.system.feign.UserClient;
import com.lego.framework.system.model.entity.User;
import com.lego.perception.log.repository.LogRepository;
import com.lego.perception.log.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @author yanglf
 * @description
 * @since 2018/12/28
 **/
@Service
public class LogServiceImpl implements ILogService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserClient userClient;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ExcelService excelService;


    @Override
    public RespVO add(Log log) {
        log.setId(UuidUtils.generateShortUuid());
        log.setCreateTime(new Date());
        Long userId = log.getUserId();
        if (userId != null) {
            RespVO<User> respVO = userClient.findUser(User.builder().id(userId).build());
            if (respVO.getRetCode() == RespConsts.SUCCESS_RESULT_CODE) {
                User user = respVO.getInfo();
                if (user != null) {
                    log.setUserName(user.getUsername());
                }
            }
        }
        mongoTemplate.save(log);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO<PagedResult<Log>> list(int pageIndex,
                                         int pageSize,
                                         String type,
                                         String tag,
                                         Long startTime,
                                         Long endTime) {
        PagedResult<Log> pagedResult = new PagedResult<>();
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.DESC, "time");
        Criteria criteria = getCriteria(type, tag, startTime, endTime);
        Query query = new Query(criteria);
        query.with(pageable).with(Sort.by(Sort.Direction.DESC, "time"));
        List<Log> logs = mongoTemplate.find(query, Log.class);
        Long totalCount = mongoTemplate.count(query, Log.class);
        Long totalPage = totalCount % pageSize == 0 ? 1 : totalCount / pageSize + 1;
        pagedResult.setPage(new com.framework.common.page.Page(pageIndex, pageSize, 0, totalCount.intValue(), totalPage.intValue()));
        pagedResult.setResultList(logs);
        return RespVOBuilder.success(pagedResult);
    }

    @Override
    public Log findLastLoginLogByUserId(String userId) {
        List<Log> logList = logRepository.findLogByUserIdAndDescOrderByOperatingTimeDesc(userId, "用户登录");
        return !CollectionUtils.isEmpty(logList) ? logList.get(0) : null;
    }

    @Override
    public RespVO exportLog(String type, String tag, Long startTime, Long endTime, HttpServletResponse response) {
        Criteria criteria = getCriteria(type, tag, startTime, endTime);
        List<LogExportVo> logExportVos = new ArrayList<>();
        Query query = new Query(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "time"));
        List<Log> logs = mongoTemplate.find(query, Log.class);
        if (CollectionUtils.isEmpty(logs)) {
            return RespVOBuilder.failure("查询不到日志信息");
        }
        for (Log log : logs) {
            LogExportVo logExportVo = new LogExportVo();
            logExportVo.setContent(log.getContent());
            logExportVo.setDesc(log.getDesc());
            logExportVo.setOperatingTime(log.getOperatingTime());
            logExportVo.setTag(log.getTag());
            logExportVo.setType(log.getType());
            logExportVo.setUserName(log.getUserName());
            logExportVos.add(logExportVo);
        }
        excelService.writeExcel(logExportVos,
                DateUtils.getDateTimeAsString(LocalDateTime.now(), "yyyyMMdd-HHmmss") + ".xlsx",
                "日志",
                null,
                response);
        return RespVOBuilder.success();
    }

    @Override
    public List<Log> list(String type, String tag, Long startTime, Long endTime,Integer limit) {
        Criteria criteria = getCriteria(type, tag, startTime, endTime);
        Query query = new Query(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "time"));
        if(limit!=null){
            query.limit(limit);
        }
        return mongoTemplate.find(query, Log.class);
    }

    private Criteria getCriteria(String type, String tag, Long startTime, Long endTime) {
        Criteria criteria = new Criteria();
        if (!StringUtils.isEmpty(type)) {
            criteria.and("type").is(type);
        }
        if (!StringUtils.isEmpty(tag)) {
            criteria.and("tag").is(tag);
        }
        if (!StringUtils.isEmpty(startTime)) {
            criteria.is("time").gt(new Date(startTime));
        }
        if (!StringUtils.isEmpty(endTime)) {
            criteria.is("time").is(new Date(endTime));
        }
        return criteria;
    }
}
