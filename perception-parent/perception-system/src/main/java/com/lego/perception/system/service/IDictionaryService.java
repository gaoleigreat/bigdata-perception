package com.lego.perception.system.service;

import com.lego.framework.system.model.entity.Dictionary;
import com.survey.lib.common.page.Page;
import com.survey.lib.common.page.PagedResult;
import com.survey.lib.common.vo.RespVO;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;

public interface IDictionaryService {

    /**
     * 查询列表
     * @param dictionary
     * @return
     */
    List<Dictionary> findList(Dictionary dictionary);

    /**
     * 查询树形结构
     * @param dictionary
     * @return
     */
    List<Dictionary> findTree(@ModelAttribute Dictionary dictionary);

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
    RespVO insert(Dictionary dictionary);

    /**
     * 批量新增
     * @param dictionaries
     * @return
     */
    RespVO insertList(List<Dictionary> dictionaries);

    /**
     * 更新
     * @param dictionary
     * @return
     */
    RespVO update(Dictionary dictionary);

    /**
     * 批量更新
     * @param dictionaries
     * @return
     */
    RespVO updateList(List<Dictionary> dictionaries);

    /**
     * 删除
     * @param id
     * @return
     */
    RespVO delete(Long id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    RespVO deleteList(List<Long> ids);
}
