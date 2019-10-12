package com.lego.kenowledge.service.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

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
    @NotEmpty(message = "请输入回复内容")
    @ApiModelProperty("回复内容")
    private String answerBody;
    @ApiModelProperty("回复人id")
    private Long createdId;
    @ApiModelProperty("回复时间")
    private Date createdDate;
    @ApiModelProperty("附件批次号")
    private String annexNum;
}
