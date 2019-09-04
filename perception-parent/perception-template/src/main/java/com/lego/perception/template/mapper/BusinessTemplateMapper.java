package com.lego.perception.template.mapper;

import com.framework.common.page.PagedResult;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.template.model.entity.BusinessTemplate;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/3 11:41
 * @desc :
 */
@Repository
public interface BusinessTemplateMapper extends Mapper<BusinessTemplate> {


    PagedResult<BusinessTemplate> selectPaged(RowBounds rowBounds);

    BusinessTemplate selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insertSelective(BusinessTemplate businessTemplate);

    Integer insertSelectiveIgnore(BusinessTemplate businessTemplate);

    Integer updateByPrimaryKeySelective(BusinessTemplate businessTemplate);

    Integer updateByPrimaryKey(BusinessTemplate businessTemplate);

    Integer batchInsert(List<BusinessTemplate> list);

    Integer batchUpdate(List<BusinessTemplate> list);

    Integer upsert(BusinessTemplate businessTemplate);

    Integer upsertSelective(BusinessTemplate businessTemplate);

    List<BusinessTemplate> query(BusinessTemplate businessTemplate);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);
}
