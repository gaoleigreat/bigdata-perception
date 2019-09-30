package com.lego.kenowledge.service.model;

import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

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
@Document(indexName = "elasticsearch", type = "knowledge")
public class Knowledge extends BaseModel {
    @Id
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("知识库类型")
    private Integer type;
    @ApiModelProperty("知识库分类(1-专家经验库;2-厂家一般故障库;3-特殊装备故障;4-其他故障)")
    private Integer classify;
    @ApiModelProperty("标签")
    private String tags;
    @ApiModelProperty("提问内容")
    private String askContent;
    @ApiModelProperty("回复内容")
    private String answerBody;
    @ApiModelProperty("描述")
    private String description;
}
