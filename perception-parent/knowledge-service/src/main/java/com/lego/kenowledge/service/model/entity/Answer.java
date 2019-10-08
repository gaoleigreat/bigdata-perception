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
 * @date : 2019/10/8 13:00
 * @desc :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @ApiModelProperty("回复id")
    private String id;
    @ApiModelProperty("回复内容")
    private String answerBody;
    @ApiModelProperty("回复人id")
    private Long answerBy;
    @ApiModelProperty("回复时间")
    private Date answerDate;
}
