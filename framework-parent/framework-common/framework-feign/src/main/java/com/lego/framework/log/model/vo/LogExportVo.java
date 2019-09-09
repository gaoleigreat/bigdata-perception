package com.lego.framework.log.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.util.Date;

/**
 * @author yanglf
 * @description
 * @since 2019/8/29
 **/
@Data
public class LogExportVo extends BaseRowModel {

    @ExcelProperty(value = "业务系统", index = 0)
    private String systemName;

    @ExcelProperty(value = "日志分类", index = 1)
    private String type;

    @ExcelProperty(value = "日志标签", index = 2)
    private String tag;


    @ExcelProperty(value = "操作人用户名", index = 3)
    private String userName;


    @ExcelProperty(value = "日志操作时间", index = 4)
    private Date operatingTime;

    @ExcelProperty(value = "日志描述", index = 5)
    private String desc;

    @ExcelProperty(value = "日志内容", index = 6)
    private String content;


}
