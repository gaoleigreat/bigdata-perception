package com.lego.perception.template.service;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.template.model.entity.BusinessTemplate;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-03 03:40:16
 * @since jdk 1.8
 */
public interface IBusinessTemplateService {


    PagedResult<BusinessTemplate> selectPaged(BusinessTemplate businessTemplate, Page page);

    BusinessTemplate selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(BusinessTemplate businessTemplate);

    Integer insertSelective(BusinessTemplate businessTemplate);

    Integer insertSelectiveIgnore(BusinessTemplate businessTemplate);

    Integer updateByPrimaryKeySelective(BusinessTemplate businessTemplate);

    Integer updateByPrimaryKey(BusinessTemplate businessTemplate);

    Integer batchInsert(List<BusinessTemplate> list);

    Integer batchUpdate(List<BusinessTemplate> list);

    /**
     * 存在即更新
     *
     * @param businessTemplate
     * @return
     */
    Integer upsert(BusinessTemplate businessTemplate);

    /**
     * 存在即更新，可选择具体属性
     *
     * @param businessTemplate
     * @return
     */
    Integer upsertSelective(BusinessTemplate businessTemplate);

    List<BusinessTemplate> query(BusinessTemplate businessTemplate);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}
