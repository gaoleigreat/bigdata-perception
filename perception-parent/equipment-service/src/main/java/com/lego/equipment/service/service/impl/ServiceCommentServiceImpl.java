package com.lego.equipment.service.service.impl;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.mybatis.utils.PageUtil;
import com.lego.equipment.service.mapper.ServiceCommentMapper;
import com.lego.equipment.service.service.IServiceCommentService;
import com.lego.framework.equipment.model.entity.ServiceComment;
import com.lego.framework.system.feign.UserClient;
import com.lego.framework.system.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-18 03:51:45
 * @since jdk 1.8
 */
@Service
public class ServiceCommentServiceImpl implements IServiceCommentService {

    @Autowired
    public ServiceCommentMapper serviceCommentMapper;

    @Autowired
    private UserClient userClient;


    @Override
    public PagedResult<ServiceComment> selectPaged(Page page, ServiceComment serviceComment) {
        PagedResult<ServiceComment> pagedResult = PageUtil.queryPaged(page, serviceComment, serviceCommentMapper);
        List<ServiceComment> resultList = pagedResult.getResultList();
        if (!CollectionUtils.isEmpty(resultList)) {
            for (ServiceComment comment : resultList) {
                setUserInfo(comment);
            }
        }
        return pagedResult;
    }

    private void setUserInfo(ServiceComment comment) {
        Long creationBy = comment.getCreationBy();
        User user = new User();
        user.setId(creationBy);
        RespVO<User> respVO = userClient.findUser(user);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return;
        }
        User info = respVO.getInfo();
        if (info != null) {
            comment.setCreationName(info.getUsername());
            comment.setCreationImg(info.getHeadImg());
        }
    }

    @Override
    public ServiceComment selectByPrimaryKey(Long id) {
        ServiceComment serviceComment = serviceCommentMapper.selectByPrimaryKey(id);
        setUserInfo(serviceComment);
        return serviceComment;
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return serviceCommentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(ServiceComment serviceComment) {
        return serviceCommentMapper.insert(serviceComment);
    }

    @Override
    public Integer insertSelective(ServiceComment serviceComment) {
        return serviceCommentMapper.insertSelective(serviceComment);
    }

    @Override
    public Integer insertSelectiveIgnore(ServiceComment serviceComment) {
        return serviceCommentMapper.insertSelectiveIgnore(serviceComment);
    }

    @Override
    public Integer updateByPrimaryKeySelective(ServiceComment serviceComment) {
        return serviceCommentMapper.updateByPrimaryKeySelective(serviceComment);
    }

    @Override
    public Integer updateByPrimaryKey(ServiceComment serviceComment) {
        return serviceCommentMapper.updateByPrimaryKey(serviceComment);
    }

    @Override
    public Integer batchInsert(List<ServiceComment> list) {
        return serviceCommentMapper.batchInsert(list);
    }

    @Override
    public Integer batchUpdate(List<ServiceComment> list) {
        return serviceCommentMapper.batchUpdate(list);
    }

    /**
     * 存在即更新
     *
     * @return
     */
    @Override
    public Integer upsert(ServiceComment serviceComment) {
        return serviceCommentMapper.upsert(serviceComment);
    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    @Override
    public Integer upsertSelective(ServiceComment serviceComment) {
        return serviceCommentMapper.upsertSelective(serviceComment);
    }

    @Override
    public List<ServiceComment> query(ServiceComment serviceComment) {
        List<ServiceComment> commentList = serviceCommentMapper.query(serviceComment);
        if (!CollectionUtils.isEmpty(commentList)) {
            for (ServiceComment comment : commentList) {
                setUserInfo(comment);
            }
        }
        return commentList;
    }

    @Override
    public Long queryTotal() {
        return serviceCommentMapper.queryTotal();
    }

    @Override
    public Integer deleteBatch(List<Long> list) {
        return serviceCommentMapper.deleteBatch(list);
    }

}
