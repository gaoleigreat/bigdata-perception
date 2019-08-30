package com.lego.perception.template.mapper;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.template.model.entity.Enumeration;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnumerationMapper extends Mapper<Enumeration> {

    /**
     * 分页查询
     * @param enumeration
     * @param page
     * @return
     */
    PagedResult<Enumeration> findPagedList(Enumeration enumeration, Page page);

    /**
     * 查询列表
     * @param enumeration
     * @return
     */
    List<Enumeration> findList(Enumeration enumeration);

    /**
     * 新增
     * @param enumeration
     * @return
     */
    Integer save(Enumeration enumeration);

    /**
     * 更新
     * @param enumeration
     * @return
     */
    Integer update(Enumeration enumeration);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer delete(Long id);
}
