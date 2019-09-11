package com.lego.framework.system.model.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "tpl_user_t")
public class User {
    /**
     * 状态，1：启用，2：锁定
     */
    @ApiModelProperty(value = "状态，1：启用，2：锁定 必填", required = false)
    private Integer status;

    /**
     * 是否删除，1：是，2：否
     */
    private Integer deleteFlag;

    /**
     * 生日
     */
    private Date birthDate;

    /**
     * 性别1：男，2：女
     */
    @ApiModelProperty(value = "性别，1：男，2：女 必填", required = false)
    private Integer gender;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", required = false)
    private String headImg;

    @ApiModelProperty(value = "用户旧密码", required = false)
    @TableField(exist = false)
    private String oldPassword;

    @TableField(exist = false)
    private List<Long> userIds;

    @ApiModelProperty(value = "身份证合照", required = false)
    private String idCardImg;

    @ApiModelProperty("职务级别，1：省部级正职，2：省部级副职，3：厅局级正职，4：厅局级副职，5：县处级正职，6：县处级副职，7：乡科级正职，8：乡科级副职，9：科员级，10：办事员级，11：未定职")
    private Integer position;

    @ApiModelProperty("政治面貌，1：中共党员，2：中共预备党员，3：共青团员，4：民革会员，5：民盟盟员，6：民建会员，7：民进会员，8：农工党党员，9：致公党党员，10：九三学社社员，11：台盟盟员，12：无党派民主人士，13：群众")
    private Integer politics;

    @ApiModelProperty("学历，1：研究生及以上，2：博士研究生，3：硕士研究生，4：大学本科，5：大学专科，6：中专技校，7：中等专科，8：职业高中，9：技工学校，10：普通高中，11：初中，12：小学，13：其他")
    private Integer education;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("角色id集合")
    @TableField(exist = false)
    private List<Long> roleIds;

    @ApiModelProperty("角色名称")
    @TableField(exist = false)
    private String roleName;

    @ApiModelProperty("角色名称集合")
    @TableField(exist = false)
    private List<String> reloNames;
    @TableField(exist = false)
    private List<String> phones;
    private Long id;

    @ApiModelProperty(value = "用户ID 必填", required = false)
    private String userId;

    @ApiModelProperty(value = "用户名称 登录必填，新增必填", required = false)
    private String username;

    @ApiModelProperty(value = "用户密码，登录必填md5加密，新增必填", required = false)
    private String password;

    @ApiModelProperty(value = "用户电话", required = false)
    private String phone;

    @ApiModelProperty(value = "用户邮箱", required = false)
    private String mail;
    @ApiModelProperty(value = "身份证号", required = false)
    @TableField(value = "idcard_no")
    private String idCardNO;
    private Date creationDate;
    private Long createdBy;
    @TableField(exist = false)
    private String createdUser;
    private Date lastUpdateDate;
    private Long lastUpdatedBy;
    @TableField(exist = false)
    private String updatedUser;

}
