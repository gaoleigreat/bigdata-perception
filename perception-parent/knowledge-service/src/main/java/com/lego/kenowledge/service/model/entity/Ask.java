package com.lego.kenowledge.service.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/10/8 12:58
 * @desc :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ask {
    @ApiModelProperty("提问id")
    private String id;
    @ApiModelProperty("提问内容")
    private String askBody;
    @ApiModelProperty("提问描述")
    private String askDesc;
    @ApiModelProperty("提问人id")
    private Long createdId;
    @ApiModelProperty("提问时间")
    private Date createdDate;
}
