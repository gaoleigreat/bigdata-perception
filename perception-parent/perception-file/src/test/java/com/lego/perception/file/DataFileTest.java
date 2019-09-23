package com.lego.perception.file;

import com.framework.common.utils.UuidUtils;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.perception.file.service.IDataFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/23 9:42
 * @desc :
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FileServerApplication.class)
public class DataFileTest {

    @Autowired
    private IDataFileService iDataFileService;


    @Test
    public void saveDataFile(){
        DataFile dataFile=new DataFile();
        dataFile.setBatchNum(UuidUtils.generateShortUuid());
        dataFile.setDataType(2);
        dataFile.setFileType("pdf");
        dataFile.setFileUrl("/file/test.pdf");
        dataFile.setPreviewUrl("http://");
        dataFile.setName("测试文件");
        dataFile.setRemark("测试文件");
        dataFile.setTags("测试,建筑");
        dataFile.setTemplateId(18L);
        iDataFileService.insert(dataFile);
    }


}
