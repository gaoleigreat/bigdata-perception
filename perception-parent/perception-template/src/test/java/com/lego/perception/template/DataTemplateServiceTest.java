package com.lego.perception.template;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.lego.framework.template.model.entity.DataTemplate;
import com.lego.framework.template.model.entity.DataTemplateItem;
import com.lego.perception.template.service.IDataTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/9/2
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TemplateServiceApplication.class)
@Slf4j
public class DataTemplateServiceTest {

    @Autowired
    private IDataTemplateService iDataTemplateService;

    @Test
    public void testFindList() {
        DataTemplate dataTemplate = new DataTemplate();
        List<DataTemplate> list = iDataTemplateService.findList(dataTemplate);
        log.info("list:{}", list);
    }


    @Test
    public void testInsert() {
        DataTemplate dataTemplate = new DataTemplate();
        dataTemplate.setTemplateName("测试数据模板2");
        dataTemplate.setTemplateCode("test_template2");
        dataTemplate.setDescription("test2");
        List<DataTemplateItem> items = new ArrayList<>();
        DataTemplateItem item1 = new DataTemplateItem();
        item1.setsField("name");
        item1.setCategory(1);
        item1.setField("name");
        item1.setTitle("姓名");
        item1.setStatus(0);
        item1.setSource("3");
        items.add(item1);
        dataTemplate.setItems(items);
        RespVO respVO = iDataTemplateService.insert(dataTemplate);
        log.info("respVo:{}",respVO);
        Assert.assertEquals(respVO.getRetCode(), RespConsts.SUCCESS_RESULT_CODE);
    }


    @Test
    public  void  testFindTreeById(){
        DataTemplate queryTemplate=new DataTemplate();
        queryTemplate.setId(38L);
        DataTemplate dataTemplate = iDataTemplateService.find(queryTemplate);
        log.info("dataTemplate:{}",dataTemplate);
        Assert.assertNotNull(dataTemplate);
    }

}




