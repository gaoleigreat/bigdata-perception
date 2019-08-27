package com.lego.perception.system.service.impl;

import com.alibaba.excel.util.CollectionUtils;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.perception.system.mapper.DictionaryMapper;
import com.lego.perception.system.service.IDictionaryService;
import com.lego.framework.system.model.entity.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanglf
 * @description
 * @since 2019/8/22
 **/
@Service
public class DictionaryServiceImpl implements IDictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public List<Dictionary> findList(Dictionary dictionary) {
        if (null == dictionary) {
            dictionary = new Dictionary();
        }
        dictionary.setDeleteFlag(1);
        return dictionaryMapper.findList(dictionary);
    }

    @Override
    public List<Dictionary> findTree(Dictionary dictionary) {
        if (null == dictionary) {
            dictionary = new Dictionary();
        }
        dictionary.setDeleteFlag(1);
        List<Dictionary> result = new ArrayList<>();
        List<Dictionary> list = dictionaryMapper.findList(dictionary);
        if (CollectionUtils.isEmpty(list)) {
            return result;
        }

        Map<String, List<Dictionary>> map = new HashMap<>();
        for (Dictionary d : list) {
            if (map.containsKey(d.getParentCode())) {
                map.get(d.getParentCode()).add(d);
            } else {
                List<Dictionary> l = new ArrayList<>();
                l.add(d);
                map.put(d.getParentCode(), l);
            }
        }
        result = map.get("-1");
        if (CollectionUtils.isEmpty(result)) {
            return result;
        }
        setChildren(result, map);
        return result;
    }

    public void setChildren(List<Dictionary> dictionaries, Map<String, List<Dictionary>> map) {
        if (CollectionUtils.isEmpty(dictionaries)) {
            return;
        }
        for (Dictionary dictionary : dictionaries) {
            dictionary.setChildren(map.get(dictionary.getCode()));
            setChildren(dictionary.getChildren(), map);
        }
    }

    @Override
    public PagedResult<Dictionary> findPagedList(Dictionary dictionary, Page page) {
        if (null == dictionary) {
            dictionary = new Dictionary();
        }
        dictionary.setDeleteFlag(1);
        return dictionaryMapper.findPagedList(dictionary, page);
    }

    @Override
    public RespVO insert(Dictionary dictionary) {
        if (null == dictionary) {
            return RespVOBuilder.failure("参数缺失");
        }
        dictionary.setCreateInfo();
        dictionaryMapper.save(dictionary);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO insertList(List<Dictionary> dictionaries) {
        if (CollectionUtils.isEmpty(dictionaries)) {
            return RespVOBuilder.failure("参数缺失");
        }
        dictionaryMapper.insertList(dictionaries);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO update(Dictionary dictionary) {
        if (null == dictionary) {
            return RespVOBuilder.failure("参数缺失");
        }
        dictionary.setUpdateInfo();
        dictionaryMapper.insert(dictionary);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO updateList(List<Dictionary> dictionaries) {
        if (CollectionUtils.isEmpty(dictionaries)) {
            return RespVOBuilder.failure("参数缺失");
        }
        for (Dictionary dictionary : dictionaries) {
            dictionary.setUpdateInfo();
        }
        dictionaryMapper.updateList(dictionaries);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO delete(Long id) {
        if (null == id) {
            return RespVOBuilder.failure("参数缺失");
        }
        dictionaryMapper.delete(id);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO deleteList(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return RespVOBuilder.failure("参数缺失");
        }
        dictionaryMapper.deleteList(ids);
        return RespVOBuilder.success();
    }
}
