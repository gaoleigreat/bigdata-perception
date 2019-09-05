package com.lego.perception.template.service;
import com.framework.common.sdto.RespVO;
import com.lego.framework.template.model.entity.TemplateItem;
import com.lego.framework.template.model.entity.ValidateResult;

import java.util.List;
import java.util.Map;

/**
 * 模板数据项
 * weihao 2019-02-27
 */
public interface ITemplateItemService<T> {

    /**
     * 查询列表
     * @param item
     * @return
     */
    List<T> findList(T item);

    /**
     * 批量新增
     * @param items
     * @return
     */
    RespVO insertList(List<T> items);

    /**
     * 批量新增树形结构
     * @param items
     * @return
     */
    RespVO insertTree(Long id, List<T> items);

    /**
     * 列表转为树形结构
     * @param items
     * @return
     */
    List<T> convertList2Tree(List<T> items);


    /**
     * 提取模板字段
     * @param items
     * @return
     */
    List<String>  convertList2String(List<T> items);

    /**
     * 树形结构转为列表
     * @param items
     * @return
     */
    List<T> convertTree2List(Long templateId, List<T> items);

    /**
     * 批量更新
     * @param items
     * @return
     */
    RespVO updateList(List<T> items);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    RespVO deleteList(List<Long> ids);

    /**
     * 空校验
     * @param item
     * @return
     */
    ValidateResult validateNull(TemplateItem item);

    /**
     * 查询 字段树
     * @param item
     * @return
     */
    Map<String, Object> findFieldTree(T item);

    /**
     * 查询 字段绝对路径 map
     * @param item
     * @return
     */
    Map<String, T> findFieldNameMap(T item);

    /**
     * 查询 字段绝对路径 map
     * @param item
     * @return
     */
    Map<String, String> findFieldName(T item);
}
