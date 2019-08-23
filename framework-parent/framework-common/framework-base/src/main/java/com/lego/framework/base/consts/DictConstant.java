package com.lego.framework.base.consts;

/**
 * @author yanglf
 * @description 业务字段
 * @since 2018/12/22
 **/
public class DictConstant {

    /**
     * 定义服务名称
     */
    public interface Service {
        String USER = "user-service";
        String AUTH = "auth-service";
        String PROJECT = "project-service";
        String SETTLEMENT = "settlement-service";
        String REPORT = "report-service";
    }


    /**
     * 平台
     */
    public interface Platform {
        String APP = "/app";
        String WEB = "/web";
    }


    /**
     * url 映射
     */
    public interface Path {
        String AUTH = "/auth";
        String RESOURCES = "/resources";
        String GROUP = "/group";
        String PROJECT = "/project";
        String SECTION = "/section";
        String WORKSPACE = "/workSpace";
        String USER = "/user";
        String LOG = "/log";
        String CONFIG = "/config";
        String BASE_POINT = "/basePoint";
        String TEMPLATE_REPORT = "/templateReport";
        String EXCEL_REPORT = "/excelReport";
        String WORD_REPORT = "/wordReport";
        String PDF_REPORT = "/pdfReport";
        String SURVEY_POINT = "/surveyPoint";
        String SURVEY_RESULT = "/surveyResult";
        String SURVEY_ORIGINAL = "/surveyOriginal";
        String SURVEY_TASK = "/surveyTask";
        String SURVEY_POINT_TYPE = "/surveyPointType";
        String SURVEY_POINT_EXCEPTION = "/surveyPointException";

    }


    /**
     * 角色
     */
    public interface Role {
        //系统管理员
        String ADMIN = "admin";
        //公司管理员
        String MASTER = "companyAdmin";
        // 标段测量管理员
        String SECTION = "master";
        // 测量员
        String SURVEYER = "surveyer";
    }


    /**
     * 角色控制 url
     */
    public interface RolePath {
        //超级管理员  创建 标段测量管理员 以及公司管理员， 标段测量管理员创建测量员
        String USER_CREATE = "/user/create";
        // 删除用户
        String USER_DELETE = "/user/delete";
        //系统管理员
        String PROJECT_CREATE = "/project/create";
        // 修改工程信息
        String PROJECT_MODIFY = "/project/modify";
        //超级管理员
        String PROJECT_DELETE = "/project/delete";
        //标段创建
        String SECTION_CREATE = "/section/create";
        // 删除标段
        String SECTION_DELETE = "/section/delete";
        // 修改标段信息
        String SECTION_MODIFY = "/section/modify";
        // 创建工区
        String WORKSPACE_CREATE = "/workspace/create";
        // 删除工区
        String WORKSPACE_DELETE = "/workspace/delete";
        // 修改工区
        String WORKSPACE_MODIFY = "/workspace/modify";
    }


    public interface TableNamePrefix {
        //测量点表前缀
        String SURVEY_POINT = "survey_point_";
        // 测量成果数据表前缀
        String SURVEY_RESULT = "survey_result_";
        // 原始数据表前缀
        String SURVEY_ORIGINAL = "survey_original_";
        // 任务表前缀
        String SURVEY_TASK = "survey_task_";
    }


    /**
     * 场景
     */
    public interface Scenes {
        // 沉降监测
        String CJFW = "cjjc";
        // 管片姿态
        String GPFW = "gpzt";
        //拱顶底部
        String GDDB = "gddb";
        // 收敛测量
        String SLCL = "slcl";
    }


    /**
     * 数据是否有效
     */
    public interface IsValid {
        int YES = 0;
        int NO = 1;
    }

    public interface ConfigName {
        String ROLE = "角色配置";
        String SENCE = "场景配置";
        String PERMISSION = "权限配置";
    }


}
