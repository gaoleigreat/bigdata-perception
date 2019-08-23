package com.lego.perception.system.init;

import com.lego.perception.system.service.IDictionaryService;
import com.lego.framework.system.model.entity.Dictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DictionaryInit implements InitializingBean {


    @Autowired
    private IDictionaryService dictionaryService;

    private Map<String, Dictionary> dictionaryMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Dictionary> list = dictionaryService.findTree(new Dictionary());
        dictionaryMap = new HashMap<>();
        setDictionary2Map(list, "");
    }

    private void setDictionary2Map(List<Dictionary> list, String parentCode){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        for(Dictionary dictionary : list){
            if(null != dictionary){
                String key = dictionary.getCode();
                if(!StringUtils.isEmpty(parentCode)){
                    key = parentCode + "." + key;
                }
                dictionaryMap.put(key, dictionary);
                setDictionary2Map(dictionary.getChildren(), key);
            }
        }
    }

    public Map<String, Dictionary> getDictionaryMap() {
        return dictionaryMap;
    }
}
