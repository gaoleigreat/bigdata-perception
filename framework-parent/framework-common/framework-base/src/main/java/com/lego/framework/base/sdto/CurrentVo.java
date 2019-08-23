package com.lego.framework.base.sdto;

import com.survey.lib.common.vo.UserSectionVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/7/9
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentVo {
    // 用户ID
    private String userId;
    // 用户角色
    private String role;
    // 用户名
    private String userName;
    // 用户权限
    private List<String> permissions;
    // 手机号
    private String phone;
    // 昵称
    private String name;
    // 所属单位ID
    private String groupId;
    // 所属单位名称
    private String groupName;
    // 工程 code
    private List<String> projects;
    // 标段
    private List<UserSectionVo> userSections;
    // 所属工区
    private List<String> workspaces;
    // 访问资源点
    private List<String>  resourcesScopes;
    private String token;
    private String deviceType;
    private boolean permissionChecked;

}
