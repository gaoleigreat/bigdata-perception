package com.lego.perception.template;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.lego.framework.template.model.entity.DataTemplateHistory;
import com.lego.framework.template.model.entity.FormTemplateHistory;
import com.lego.perception.template.service.IHistoryTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yanglf
 * @description
 * @since 2019/9/2
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TemplateServiceApplication.class)
@Slf4j
public class HistoryTemplateServiceTest {

    @Autowired
    @Qualifier(value = "dataTemplateHistoryServiceImpl")
    private IHistoryTemplateService<DataTemplateHistory> iDataTemplateHistoryService;

    @Autowired
    @Qualifier(value = "formTemplateHistoryServiceImpl")
    private IHistoryTemplateService<FormTemplateHistory> iFormTemplateHistoryService;


    @Test
    public void testInsertDataTemplate() {
        DataTemplateHistory dataTemplateHistory = new DataTemplateHistory();
        dataTemplateHistory.setTemplateCode("his_data_01");
        dataTemplateHistory.setTemplateName("历史数据模板001");
        dataTemplateHistory.setDescription("test");
        dataTemplateHistory.setTag("2019");
        RespVO respVO = iDataTemplateHistoryService.insert(dataTemplateHistory);
        Assert.assertEquals(respVO.getRetCode(), RespConsts.SUCCESS_RESULT_CODE);
    }


    @Test
    public void testFindDataTemplateList() {
        DataTemplateHistory dataTemplateHistory = iDataTemplateHistoryService.find("his_data_01", "2019");
        log.info("dataTemplateHistory:{}", dataTemplateHistory);
    }

    @Test
    public void testInsertFormTemplate() {
        FormTemplateHistory formTemplateHistory = new FormTemplateHistory();
        formTemplateHistory.setTag("2019");
        formTemplateHistory.setTemplateCode("his_form_001");
        formTemplateHistory.setTemplateName("历史表单模板001");
        RespVO respVO = iFormTemplateHistoryService.insert(formTemplateHistory);
        Assert.assertEquals(respVO.getRetCode(), RespConsts.SUCCESS_RESULT_CODE);
    }


    @Test
    public void testFindFormTemplateList(){
        FormTemplateHistory templateHistory = iFormTemplateHistoryService.find("his_form_001", "2019");
        log.info("templateHistory:{}",templateHistory);
    }


}
