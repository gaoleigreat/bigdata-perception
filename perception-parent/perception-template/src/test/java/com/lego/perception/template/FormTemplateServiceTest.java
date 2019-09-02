package com.lego.perception.template;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.template.service.IFormTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/9/2
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TemplateServiceApplication.class)
@Slf4j
public class FormTemplateServiceTest {

    @Autowired
    private IFormTemplateService iFormTemplateService;


    @Test
    public void testFindList(){
        FormTemplate formTemplate=new FormTemplate();
        List<FormTemplate> list = iFormTemplateService.findList(formTemplate);
        log.info("list:{}",list);
    }

    @Test
    public void  testInsert(){
        FormTemplate formTemplate=new FormTemplate();
        formTemplate.setCategory(1L);
        formTemplate.setDataTemplateId(38L);
        formTemplate.setTemplateCode("test_form_01");
        formTemplate.setTemplateName("測試表單模板001");
        formTemplate.setDescription("test");
        RespVO respVO = iFormTemplateService.insert(formTemplate);
        Assert.assertEquals(respVO.getRetCode(), RespConsts.SUCCESS_RESULT_CODE);
    }




}
