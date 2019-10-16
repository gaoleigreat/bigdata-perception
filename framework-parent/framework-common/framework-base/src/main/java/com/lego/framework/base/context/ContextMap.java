package com.lego.framework.base.context;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/10/15 16:05
 * @desc :
 */
@Component
public class ContextMap {

    private static Map<Integer, String> docTypes = new HashMap<>();


    public static Map<Integer, String> getDocTypes() {
        return docTypes;
    }

    @PostConstruct
    public void initMap() {
        initDocTypes();
    }

    private void initDocTypes() {
        //0-机械图纸；1-电气图纸；2-液压图纸；3-维修方案；4-会议纪要；5-转场记录；6-维修合同;7-其他
        docTypes.put(0, "机械图纸");
        docTypes.put(1, "电器图纸");
        docTypes.put(2, "液压图纸");
        docTypes.put(3, "维修方案");
        docTypes.put(4, "会议纪要");
        docTypes.put(5, "转场记录");
        docTypes.put(6, "维修合同");
        docTypes.put(7, "其他");
    }


    @PreDestroy
    public void destroy() {
        docTypes.clear();
        docTypes = null;
    }

}
