package com.lego.perception.system;

import com.lego.framework.base.utils.DateUtils;
import com.lego.framework.system.model.entity.Project;
import com.lego.perception.system.service.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/9/2
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
@Slf4j
public class ProjectServiceTest {

    @Autowired
    private IProjectService iProjectService;


    @Test
    public void testFindList() {
        List<Project> list = iProjectService.query(Project.builder().build());
        log.info("project list:[{}]", list);
        Assert.assertTrue(list.size() > 0);
    }


    @Test
    public void testInsert() {
        Project project = Project.builder()
                .name("测试工程1")
                .address("陕西西安")
                .startTime(DateUtils.stringToTime("2018-12-10 12:09:00"))
                .endTime(new Date())
                .build();
        Integer insert = iProjectService.insertSelective(project, 1L);
        log.info("project:[{}]", project);
        Assert.assertTrue(insert > 0);
    }


}
