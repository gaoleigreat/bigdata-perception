package com.lego.perception.system.service.impl;
import com.lego.perception.system.mapper.UserRoleMapper;
import com.lego.perception.system.service.IUserRoleService;
import com.lego.framework.system.model.entity.UserRole;
import com.survey.lib.common.page.Page;
import com.survey.lib.common.page.PagedResult;
import com.survey.lib.common.vo.RespVO;
import com.survey.lib.common.vo.RespVOBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserRoleServiceImpl implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> findList(UserRole userRole) {

        return userRoleMapper.findList(userRole);
    }

    @Override
    public PagedResult<UserRole> findPagedList(UserRole userRole, Page page) {

        return userRoleMapper.findPagedList(userRole, page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespVO insertList(List<UserRole> userRolePrograms) {
        if(CollectionUtils.isEmpty(userRolePrograms)){
            return RespVOBuilder.failure("参数缺失");
        }
        for(UserRole userRoleProgram : userRolePrograms){
            userRoleProgram.setCreateInfo();
        }
        userRoleMapper.insertList(userRolePrograms);
        return RespVOBuilder.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespVO delete(UserRole userRole) {
        if(null == userRole || (null == userRole.getId() && (null == userRole.getUserId() || null == userRole.getRoleId()))){
            return RespVOBuilder.failure("参数缺失");
        }
        userRoleMapper.delete(userRole);
        return RespVOBuilder.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespVO deleteList(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return RespVOBuilder.failure("参数缺失");
        }
        userRoleMapper.deleteList(ids);
        return RespVOBuilder.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespVO deleteListByUserIds(List<Long> userIds) {
        if(CollectionUtils.isEmpty(userIds)){
            return RespVOBuilder.failure("参数缺失");
        }
        for (int i = 0; i < userIds.size(); i++) {
            UserRole query = new UserRole();
            query.setUserId(userIds.get(i));
            List<UserRole> userRoleList = userRoleMapper.findList(query);
            List<Long> ids = new ArrayList<>();
            if(userRoleList!=null&&userRoleList.size()>0){
                for (int j = 0; j < userRoleList.size(); j++) {
                    ids.add(userRoleList.get(j).getId());
                }
                userRoleMapper.deleteList(ids);
            }
        }
        return RespVOBuilder.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespVO save(Long userId, List<UserRole> userRoles) {
        //1.校验
        if(null == userId){
            return RespVOBuilder.failure("参数缺失");
        }
        //2,查询原始数据
        UserRole queryParam = new UserRole();
        queryParam.setUserId(userId);
        List<UserRole> originList = userRoleMapper.findList(queryParam);

        //3.
        List<UserRole> toInsert = new ArrayList<>();
        List<Long> toDelete = new ArrayList<>();

        List<Long> originIds = new ArrayList<>();
        List<String> originUserIds = new ArrayList<>();
        List<String> newUserIds = new ArrayList<>();


        //如果新数据是空，删除原始数据，返回；
        if(CollectionUtils.isEmpty(userRoles)){
            userRoleMapper.deleteList(originIds);
            return RespVOBuilder.success();
        }else{
            for(UserRole userRole : userRoles){
                String key = userRole.getRoleId()+"";
                newUserIds.add(key);
                if(originUserIds.contains(key)){
                    continue;
                }
                toInsert.add(userRole);
            }
        }

        for(UserRole userRole : originList){
            String key = userRole.getRoleId()+"";
            if(newUserIds.contains(key)){
                continue;
            }
            toDelete.add(userRole.getId());
        }

        //保存数据
        if(!CollectionUtils.isEmpty(toInsert)){
            userRoleMapper.insertList(toInsert);
        }

        if(!CollectionUtils.isEmpty(toDelete)){
            userRoleMapper.deleteList(toDelete);
        }

        return RespVOBuilder.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespVO updateAndInsert(UserRole userRole) {
        if(null == userRole || null == userRole.getRoleId() || null == userRole.getUserId()){
            return RespVOBuilder.failure("参数缺失");
        }
        List<UserRole> originList = findList(userRole);
        if(CollectionUtils.isEmpty(originList)){
            insertList(Arrays.asList(new UserRole[]{userRole}));
        }
        return RespVOBuilder.success();
    }
}
