package com.lego.perception.template;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.lego.framework.template.model.entity.Enumeration;
import com.lego.perception.template.service.IEnumerationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * Version : 1.0
 * Author  : yanglf
 * Date    : 2019/9/2 16:55
 * Desc    :
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TemplateServiceApplication.class)
@Slf4j
public class EnumerationServiceTest {


    @Autowired
    private IEnumerationService iEnumerationService;


    @Test
    public void testInsert() {
        Enumeration enumeration = new Enumeration();
        enumeration.setEnumName("年份");
        enumeration.setDescription("年份");
        enumeration.setEnumCode("year");
        RespVO respVO = iEnumerationService.insert(enumeration);
        Assert.assertEquals(respVO.getRetCode(), RespConsts.SUCCESS_RESULT_CODE);
    }


    @Test
    public void testFindList() {
        Enumeration queryEnumeration = new Enumeration();
        queryEnumeration.setEnumCode("year");
        Enumeration enumeration = iEnumerationService.find(queryEnumeration);
        log.info("enumeration:{}", enumeration);
    }


}
