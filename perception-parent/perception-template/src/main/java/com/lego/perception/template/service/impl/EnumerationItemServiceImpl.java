package com.lego.perception.template.service.impl;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.EnumerationItem;
import com.lego.perception.template.mapper.EnumerationItemMapper;
import com.lego.perception.template.service.IEnumerationItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
@Slf4j
public class EnumerationItemServiceImpl implements IEnumerationItemService {

    @Autowired
    private EnumerationItemMapper enumerationItemMapper;

    @Override
    public List<EnumerationItem> findList(EnumerationItem item) {

        return enumerationItemMapper.findList(item);
    }

    @Override
    public EnumerationItem finItem(EnumerationItem item) {
        return enumerationItemMapper.findItem(item);
    }

    @Override
    public RespVO insertList(List<EnumerationItem> lst) {
        if(CollectionUtils.isEmpty(lst)){
            return RespVOBuilder.failure("参数缺失");
        }
        for(EnumerationItem item : lst){
            if(null == item || null == item.getEnumId() || StringUtils.isEmpty(item.getLabel()) || null == item.getValue()){
                return RespVOBuilder.failure("参数缺失");
            }
        }
        enumerationItemMapper.insertList(lst);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO deleteList(List<Long> lst) {
        if(CollectionUtils.isEmpty(lst)){
            return RespVOBuilder.failure("参数缺失");
        }
        enumerationItemMapper.deleteList(lst);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO deleteByEnumId(Long enumId) {
        if(null == enumId){
            return RespVOBuilder.failure("参数缺失");
        }
        enumerationItemMapper.deleteByEnumId(enumId);
        return RespVOBuilder.success();
    }
}
