package com.lego.perception.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.lego.framework.business.model.entity.BusinessTable;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.SearchParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/3 18:05
 * @desc :
 */
public interface ICrudService {

    /**
     * 创建业务表
     *
     * @param formTemplate
     * @return
     */
    RespVO createBusinessTable(FormTemplate formTemplate);


    /**
     * 新增业务数据
     *
     * @param formTemplate
     * @param data
     * @return
     */
    RespVO insertBusinessData(FormTemplate formTemplate, List<Map<String, Object>> data);


    /**
     * 查询业务数据
     *
     * @param tableName
     * @param params
     * @return
     */
    RespVO<RespDataVO<Map<String,Object>>> queryBusinessData(String tableName, List<SearchParam> params);


    /**
     * 分页查询业务数据
     *
     * @param tableName
     * @param params
     * @param page
     * @return
     */
    RespVO<PagedResult<Map<String, Object>>> queryBusinessDataPaged(String tableName, List<SearchParam> params, Page page);


    /**
     * 更新业务数据
     *
     * @param tableName
     * @param data
     * @return
     */
    RespVO updateBusinessData(String tableName, Map<String, Object> data);


    /**
     * 删除业务数据
     *
     * @param tableName
     * @param data
     * @return
     */
    RespVO delBusinessData(String tableName, Map<String, Object> data);


    /**
     * 上传业务数据
     *
     * @param formTemplate
     * @param file
     * @return
     */
    RespVO uploadBusinessData(FormTemplate formTemplate, MultipartFile file);

    /**
     * 导出业务数据
     *
     * @param formTemplate
     * @param searchParams
     * @return
     */
    void downloadBusinessData(FormTemplate formTemplate, List<SearchParam> searchParams, HttpServletResponse response);
}
