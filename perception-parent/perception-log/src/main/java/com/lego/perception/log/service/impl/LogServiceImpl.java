package com.lego.perception.log.service.impl;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.utils.UuidUtils;
import com.lego.framework.log.model.entity.Log;
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


    @Override
    public RespVO add(Log log) {
        log.setId(UuidUtils.generateShortUuid());
        log.setCreateTime(new Date());
        Long userId = log.getUserId();
        if (userId != null) {
            RespVO<User> respVO = userClient.findUserById(User.builder().id(userId).build());
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
        List<Log> logList = logRepository.findLogByUserIdAndDescOrderByTimeDesc(userId, "用户登录");
        return !CollectionUtils.isEmpty(logList) ? logList.get(0) : null;
    }
}
