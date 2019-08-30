package com.lego.perception.template.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.Enumeration;
import com.lego.framework.template.model.entity.EnumerationItem;
import com.lego.framework.template.model.entity.ValidateResult;
import com.lego.perception.template.mapper.EnumerationMapper;
import com.lego.perception.template.service.IEnumerationItemService;
import com.lego.perception.template.service.IEnumerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class EnumerationServiceImpl implements IEnumerationService {

    @Autowired
    private EnumerationMapper enumerationMapper;

    @Autowired
    private IEnumerationItemService enumerationItemService;

    @Override
    public PagedResult<Enumeration> findPagedList(Enumeration enumeration, Page page) {

        return enumerationMapper.findPagedList(enumeration, page);
    }

    @Override
    public List<Enumeration> findList(Enumeration enumeration) {

        List<Enumeration> enumerationList = enumerationMapper.findList(enumeration);
        if (!CollectionUtils.isEmpty(enumerationList)) {
            List<Long> enumIds = new ArrayList<>();
            for (Enumeration e : enumerationList) {
                enumIds.add(e.getId());
            }

            EnumerationItem queryParam = new EnumerationItem();
            queryParam.setEnumIds(enumIds);
            List<EnumerationItem> items = enumerationItemService.findList(queryParam);

            Map<Long, List<EnumerationItem>> map = new HashMap<>();
            for (EnumerationItem item : items) {
                if (map.containsKey(item.getEnumId())) {
                    map.get(item.getEnumId()).add(item);
                } else {
                    List<EnumerationItem> lst = new ArrayList<>();
                    lst.add(item);
                    map.put(item.getEnumId(), lst);
                }
            }

            for (Enumeration e : enumerationList) {
                e.setItems(map.get(e.getId()));
            }
        }

        return enumerationList;
    }

    @Override
    public Enumeration find(Enumeration enumeration) {
        Enumeration e = null;
        List<Enumeration> enumerationList = enumerationMapper.findList(enumeration);
        if (!CollectionUtils.isEmpty(enumerationList)) {
            e = enumerationList.get(0);

            EnumerationItem queryParam = new EnumerationItem();
            queryParam.setEnumId(e.getId());
            List<EnumerationItem> items = enumerationItemService.findList(queryParam);
            e.setItems(items);
        }
        return e;
    }

    @Override
    public RespVO insert(Enumeration enumeration) {
        ValidateResult v = validateNull(enumeration);
        if (!v.getResult()) {
            return RespVOBuilder.failure(v.getMsg());
        }

        Enumeration queryParam = new Enumeration();
        queryParam.setEnumCode(enumeration.getEnumCode());
        List lst = findList(queryParam);
        if (!CollectionUtils.isEmpty(lst)) {
            return RespVOBuilder.failure("枚举编码重复");
        }
        enumeration.setCreationDate(new Date());
        enumeration.setLastUpdateDate(new Date());
        enumerationMapper.insert(enumeration);

        if (!CollectionUtils.isEmpty(enumeration.getItems())) {
            Long enumId = enumeration.getId();
            for (EnumerationItem item : enumeration.getItems()) {
                item.setEnumId(enumId);
            }
            enumerationItemService.insertList(enumeration.getItems());
        }
        return RespVOBuilder.success();
    }

    private ValidateResult validateNull(Enumeration enumeration) {
        ValidateResult v = new ValidateResult();
        v.setResult(false);
        if (null == enumeration) {
            v.setMsg("参数错误");
            return v;
        }

        if (StringUtils.isEmpty(enumeration.getEnumCode())) {
            v.setMsg("枚举编码不能为空");
            return v;
        }

        if (StringUtils.isEmpty(enumeration.getEnumName())) {
            v.setMsg("枚举名称不能为空");
            return v;
        }
        v.setResult(true);
        return v;
    }

    @Override
    public RespVO update(Enumeration enumeration) {
        if (null == enumeration || null == enumeration.getId()) {
            return RespVOBuilder.failure("参数缺失");
        }

        //验证重复
        if (null != enumeration.getEnumCode()) {
            Enumeration queryParam = new Enumeration();
            queryParam.setEnumCode(enumeration.getEnumCode());
            List<Enumeration> lst = findList(queryParam);
            if (!CollectionUtils.isEmpty(lst) && !lst.get(0).getId().equals(enumeration.getId())) {
                return RespVOBuilder.failure("枚举编码重复");
            }
        }
        //更新
        enumeration.setLastUpdateDate(new Date());
        enumerationMapper.update(enumeration);

        enumerationItemService.deleteByEnumId(enumeration.getId());
        if (!CollectionUtils.isEmpty(enumeration.getItems())) {
            for (EnumerationItem item : enumeration.getItems()) {
                item.setEnumId(enumeration.getId());
            }
            enumerationItemService.insertList(enumeration.getItems());
        }

        return RespVOBuilder.success();
    }

    @Override
    public RespVO delete(Long id) {
        //删除枚举项
        enumerationMapper.delete(id);
        enumerationItemService.deleteByEnumId(id);
        return RespVOBuilder.success();
    }
}
