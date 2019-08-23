package com.lego.perception.system.mapper;

import com.lego.survey.lib.mybatis.mapper.Mapper;
import com.lego.framework.system.model.entity.Dictionary;
import com.survey.lib.common.page.Page;
import com.survey.lib.common.page.PagedResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryMapper extends Mapper<Dictionary> {

    /**
     * 查询列表
     * @param dictionary
     * @return
     */
    List<Dictionary> findList(Dictionary dictionary);

    /**
     * 分页查询列表
     * @param dictionary
     * @param page
     * @return
     */
    PagedResult<Dictionary> findPagedList(Dictionary dictionary, Page page);

    /**
     * 新增
     * @param dictionary
     * @return
     */
    Integer save(Dictionary dictionary);

    /**
     * 批量新增
     * @param dictionaries
     * @return
     */
    Integer insertList(List<Dictionary> dictionaries);

    /**
     * 更新
     * @param dictionary
     * @return
     */
    Integer update(Dictionary dictionary);

    /**
     * 批量更新
     * @param dictionaries
     * @return
     */
    Integer updateList(List<Dictionary> dictionaries);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer delete(Long id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer deleteList(List<Long> ids);
}
