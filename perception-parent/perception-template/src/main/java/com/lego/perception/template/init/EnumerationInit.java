package com.lego.perception.template.init;

import com.lego.framework.template.model.entity.Enumeration;
import com.lego.perception.template.service.IEnumerationService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EnumerationInit implements InitializingBean {

    private Map<String, Enumeration> enumerationCodeMap;

    private Map<Long, Enumeration> enumerationIdMap;

    @Autowired
    private IEnumerationService enumerationService;

    @Override
    public void afterPropertiesSet() throws Exception {
        initEnumMap();
    }

    public void initEnumMap() {
        List<Enumeration> enumerationList = enumerationService.findList(new Enumeration());
        Map<String, Enumeration> newEnumerationCodeMap = new HashMap<>();
        Map<Long, Enumeration> newEnumerationIdMap = new HashMap<>();
        for(Enumeration e : enumerationList){
            newEnumerationCodeMap.put(e.getEnumCode(), e);
            newEnumerationIdMap.put(e.getId(), e);
        }
        enumerationCodeMap = newEnumerationCodeMap;
        enumerationIdMap = newEnumerationIdMap;
    }

    public Map<String, Enumeration> getEnumerationCodeMap() {
        return enumerationCodeMap;
    }

    public Map<Long, Enumeration> getEnumerationIdMap() {
        return enumerationIdMap;
    }
}
