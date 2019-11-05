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
        //0-(新机-设计联络)；1-(新机-合同)；2-(新机-图纸)；3-(新机-三验报告(工厂、公司、百米))；4-(旧机-动态勘验报告)；
        // 5-(旧机-维修方案)；6-(旧机-静态勘验报告);7-(旧机-专项方案);8-(旧机-实验过程资料);9-旧机-三研报告（工厂、公司、百米）
        // 10-(旧机-决算资料)
        docTypes.put(0, "新机-设计联络");
        docTypes.put(1, "新机-合同");
        docTypes.put(2, "新机-图纸");
        docTypes.put(3, "新机-三验报告（工厂、公司、百米）)");
        docTypes.put(4, "旧机-动态勘验报告");
        docTypes.put(5, "旧机-维修方案");
        docTypes.put(6, "旧机-静态勘验报告");
        docTypes.put(7, "旧机-专项方案");
        docTypes.put(8, "旧机-实验过程资料");
        docTypes.put(9, "旧机-三研报告（工厂、公司、百米）");
        docTypes.put(10, "旧机-决算资料");
    }


    @PreDestroy
    public void destroy() {
        docTypes.clear();
        docTypes = null;
    }

}
