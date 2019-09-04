package com.lego.framework.business.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/4 16:28
 * @desc :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessTable {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("表名")
    private String tableName;
    @ApiModelProperty("字段")
    private Map<String, Object> params;
}
