package com.lego.perception.system.service.impl;

import com.lego.perception.system.mapper.PermissionMapper;
import com.lego.perception.system.service.IPermissionService;
import com.lego.perception.system.service.IRolePermissionService;
import com.lego.framework.system.model.Permission;
import com.survey.lib.common.vo.RespVO;
import com.survey.lib.common.vo.RespVOBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private IRolePermissionService rolePermissionService;


    @Override
    public List<Permission> findList(Permission permission) {

        return permissionMapper.findList(permission);
    }

    @Override
    public List<Map<String, Object>> findTree(Permission permission) {
        List<Permission> permissions = permissionMapper.findList(permission);

        Map<String, Map<String,List<Permission>>> map = new HashMap<>();
        if(CollectionUtils.isEmpty(permissions)){
            return Collections.EMPTY_LIST;
        }

        for(Permission p : permissions){
            if(null == p || StringUtils.isEmpty(p.getScope()) || StringUtils.isEmpty(p.getPrName()) || StringUtils.isEmpty(p.getRId())){
                continue;
            }
            if(map.containsKey(p.getScope())){
                Map<String, List<Permission>> m1 = map.get(p.getScope());
                if(m1.containsKey(p.getRId())){
                    m1.get(p.getRName()).add(p);
                }else{
                    List<Permission> list = new ArrayList<>();
                    list.add(p);
                    m1.put(p.getRName(), list);
                }
            }else{
                Map<String, List<Permission>> m1 = new HashMap<>();
                List<Permission> list = new ArrayList<>();
                list.add(p);
                m1.put(p.getRName(),list);
                map.put(p.getScope(), m1);
            }
        }
        //转为前台的json格式
        List<Map<String, Object>> resultList = new ArrayList<>();

        for(String key : map.keySet()){
            Map<String, Object> m1 = new HashMap<String, Object>();

            Map<String, List<Permission>> mm = map.get(key);
            List<Map<String, Object>> list = new ArrayList<>();

            for(String k : mm.keySet()){
                List<Permission> permissionList = mm.get(k);
                Map<String, Object> m2 = new HashMap<String, Object>();
                m2.put("rName", k);
                m2.put("children", permissionList);
                list.add(m2);
            }

            m1.put("rName",key);
            m1.put("children", list);
            resultList.add(m1);
        }
        return resultList;
    }

    @Override
    public RespVO insertList(List<Permission> permissions) {
        if(CollectionUtils.isEmpty(permissions)){
            return RespVOBuilder.failure("参数缺失");
        }
        permissionMapper.insertList(permissions);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO deleteList(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return RespVOBuilder.failure("参数缺失");
        }
        //删除权限点
        permissionMapper.deleteList(ids);
        //删除已绑定角色
        rolePermissionService.deleteList(ids);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO save(String scope, List<Permission> permissions) {
        if(StringUtils.isEmpty(scope)){
            return RespVOBuilder.failure("参数缺失");
        }

        Permission queryParam = new Permission();
        queryParam.setScope(scope);
        List<Permission> origin = findList(queryParam);
        List<Long> originIds = new ArrayList<>();

        List<Permission> temp = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for(Permission permission: permissions){
            if(StringUtils.isEmpty(permission.getRId()) || StringUtils.isEmpty(permission.getPrId())){
                continue;
            }
            String key = permission.getRId() + "$" + permission.getPrId();
            if(!set.contains(key)){
                permission.setScope(scope);
                set.add(key);
                temp.add(permission);
            }
        }
        permissions.clear();
        permissions.addAll(temp);


        if(CollectionUtils.isEmpty(origin)){
            insertList(permissions);
            return RespVOBuilder.success();
        }

        Map<String, Permission> originMap = new HashMap<String,Permission>();
        for(Permission permission : origin){
            originMap.put(permission.getRId()+"$"+permission.getPrId(), permission);
            originIds.add(permission.getId());
        }

        Map<String, Permission> newMap = new HashMap<String,Permission>();

        List<Long> deletes = new ArrayList<Long>();
        List<Permission> inserts = new ArrayList<Permission>();

        if(CollectionUtils.isEmpty(permissions)){
            deleteList(originIds);
            return RespVOBuilder.success();
        }else{
            for(Permission permission: permissions){
                permission.setScope(scope);
                String key = permission.getRId() + "$" + permission.getPrId();
                newMap.put(key, permission);
                if(!originMap.containsKey(key)){
                    inserts.add(permission);
                }
            }
        }

        for(Permission permission : origin){
            String key = permission.getRId() + "$" + permission.getPrId();
            if(!newMap.containsKey(key)){
                deletes.add(permission.getId());
            }
        }

        if(!CollectionUtils.isEmpty(deletes)){
            deleteList(deletes);
        }

        if(!CollectionUtils.isEmpty(inserts)){
            insertList(inserts);
        }

        log.info("synchronize permission success");
        return RespVOBuilder.success();
    }

    @Override
    public List<Permission> findUserPermissions(Long userId) {

        return permissionMapper.findUserPermissions(userId);
    }

}
