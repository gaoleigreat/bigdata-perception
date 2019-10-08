package com.lego.kenowledge.service.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;
import java.util.List;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/30 11:30
 * @desc :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeVo {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("知识库分类(1-专家经验库;2-厂家一般故障库;3-特殊装备故障;4-其他故障)")
    private Integer classify;
    @ApiModelProperty("标签")
    private List<String> tags;
    @ApiModelProperty("提问内容")
    private AskVo askVo;
    @ApiModelProperty("回复内容")
    private List<AnswerVo> answerVos;
    @ApiModelProperty("创建时间")
    private Date creationDate;
    @ApiModelProperty("创建人id")
    private Long createdBy;
    @ApiModelProperty("更新时间")
    private Date lastUpdateDate;
    @ApiModelProperty("更新人id")
    private Long lastUpdatedBy;
}
