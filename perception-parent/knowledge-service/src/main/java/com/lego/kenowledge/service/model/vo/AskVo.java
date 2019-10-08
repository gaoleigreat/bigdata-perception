package com.lego.kenowledge.service.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class AskVo {
    @ApiModelProperty("提问id")
    private String id;
    @ApiModelProperty("提问内容")
    private String askBody;
    @ApiModelProperty("提问描述")
    private String askDesc;
    @ApiModelProperty("提问人id")
    private Long askBy;
    @ApiModelProperty("提问时间")
    private Date askDate;
    @ApiModelProperty("回复数")
    private Integer answerCount;
}
