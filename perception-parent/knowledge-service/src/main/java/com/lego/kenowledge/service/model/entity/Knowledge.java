package com.lego.kenowledge.service.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@Document(indexName = "equipment", type = "knowledge")
public class Knowledge {
    @Id
    @ApiModelProperty("id")
    private String id;
    @Field(index = false)
    @ApiModelProperty("知识库分类(1-专家经验库;2-厂家一般故障库;3-特殊装备故障;4-其他故障)")
    private Integer classify;
    @ApiModelProperty("标签")
    private List<String> tags;
    @ApiModelProperty("提问内容")
    private Ask ask;
    @ApiModelProperty("回复内容")
    private List<Answer> answers;
    @ApiModelProperty("创建时间")
    @Field(index = false)
    private Date createdDate;
    @ApiModelProperty("创建人id")
    @Field(index = false)
    private Long createdId;
    @ApiModelProperty("更新时间")
    @Field(index = false)
    private Date updatedDate;
    @Field(index = false)
    @ApiModelProperty("更新人id")
    private Long updatedId;
}
