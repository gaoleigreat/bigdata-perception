package com.lego.perception.business;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.FormTemplateItem;
import com.lego.perception.business.service.IBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/4 14:24
 * @desc :
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BusinessServiceApplication.class)
@Slf4j
public class BusinessServiceTest {


    @Autowired
    @Qualifier(value = "mySqlBusinessServiceImpl")
    private IBusinessService iBusinessService;


    @Test
    public void testCreateBusinessTable() {
        FormTemplate formTemplate = new FormTemplate();
        formTemplate.setDescription("tpl_device_info");
        formTemplate.setTemplateName("device_business_01");
        List<FormTemplateItem> items = new ArrayList<>();
        items.add(getFormTemplateItem(1, 1, "设备名称", "name", 1, null));

        items.add(getFormTemplateItem(9, 2, "设备类型", "type", 1, null));
        items.add(getFormTemplateItem(2, 1, "设备型号", "model", 1, null));
        items.add(getFormTemplateItem(9, 1, "当前状态(0-停止运行;1-正则运行)", "status", 1, "0"));

        items.add(getFormTemplateItem(3, 2, "创建时间", "creation_date", 1, null));
        items.add(getFormTemplateItem(9, 2, "创建用户id", "created_by", 1, null));

        formTemplate.setItems(items);
        RespVO respVO = iBusinessService.createBusinessTable(formTemplate);
        log.info("respVO:{}", respVO);
        Assert.assertEquals(respVO.getRetCode(), RespConsts.SUCCESS_RESULT_CODE);
    }


    @Test
    public void testInsertBusinessData() {
        FormTemplate formTemplate = new FormTemplate();
        formTemplate.setDescription("tpl_device_info");
        formTemplate.setTemplateName("device_business_01");
        List<FormTemplateItem> items = new ArrayList<>();
        items.add(getFormTemplateItem(1, 1, "设备名称", "name", 1, null));

        items.add(getFormTemplateItem(9, 2, "设备类型", "type", 1, null));
        items.add(getFormTemplateItem(2, 1, "设备型号", "model", 1, null));
        items.add(getFormTemplateItem(9, 1, "当前状态(0-停止运行;1-正则运行)", "status", 1, "0"));

        items.add(getFormTemplateItem(3, 2, "创建时间", "creation_date", 1, null));
        items.add(getFormTemplateItem(9, 2, "创建用户id", "created_by", 1, null));

        formTemplate.setItems(items);

        Map<String, Object> mp1 = new HashMap<>();
        mp1.put("name", "盾构机2");
        mp1.put("type", 1);
        mp1.put("model", "dg-002");
        mp1.put("creation_date", new Date());
        List<Map<String,Object>> maps=new ArrayList<>();
        maps.add(mp1);
        RespVO respVO = iBusinessService.insertBusinessData(formTemplate, maps,1L);
        log.info("respVO:{}", respVO);
        Assert.assertEquals(respVO.getRetCode(), RespConsts.SUCCESS_RESULT_CODE);
    }


    @Test
    public void testQuery() {
        Map<String, Object> mp1 = new HashMap<>();
        mp1.put("name", "盾构机1");
        RespVO respVO = iBusinessService.queryBusinessData("tpl_device_info", mp1);
        log.info("respVO:{}", respVO);
        Assert.assertEquals(respVO.getRetCode(), RespConsts.SUCCESS_RESULT_CODE);
    }


    private FormTemplateItem getFormTemplateItem(Integer category,
                                                 Integer isRequired,
                                                 String desc,
                                                 String field,
                                                 Integer status,
                                                 String defaultValue) {
        FormTemplateItem item = new FormTemplateItem();
        item.setCategory(category);
        item.setIsRequired(isRequired);
        item.setDescription(desc);
        item.setField(field);
        item.setStatus(status);
        item.setTitle(desc);
        item.setDefaultValue(defaultValue);
        return item;
    }

}
