package com.lego.framework.equipment.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author itar
 * @mail wuhandzy@gmail.com
 * @date 2019-10-08 06:16:21
 * @since jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FittingVo extends BaseRowModel {
    @ExcelProperty(value = "序号", index = 0)
    private String num;

    /**
     * 中心物料号
     */
    @ExcelProperty(value = "中心物料号", index = 1)
    private String centerMaterialsNum;
    /**
     * 出厂物料号
     */
    @ExcelProperty(value = "出厂物料号", index = 2)
    private String factoryMaterialNum;
    /**
     * 物料名称
     */
    @ExcelProperty(value = "物料名称", index = 3)
    private String materialName;
    /**
     * 物料描述
     */
    @ExcelProperty(value = "物料描述", index = 4)
    private String materialDesc;
    /**
     * 单位
     */
    @ExcelProperty(value = "单位", index = 5)
    private String unit;
    /**
     * 数量
     */
    @ExcelProperty(value = "数量", index = 6)
    private String count;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 7)
    private String remark;
}
