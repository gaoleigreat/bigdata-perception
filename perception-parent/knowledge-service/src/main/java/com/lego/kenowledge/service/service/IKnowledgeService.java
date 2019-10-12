package com.lego.kenowledge.service.service;

import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.lego.kenowledge.service.model.entity.Answer;
import com.lego.kenowledge.service.model.entity.Ask;
import com.lego.kenowledge.service.model.entity.Knowledge;
import com.lego.kenowledge.service.model.vo.AskVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/10/12 11:07
 * @desc :
 */
public interface IKnowledgeService {
    /**
     * 新增提问
     * @param ask
     * @param classify
     * @param files
     * @param tags
     * @return
     */
    RespVO saveAsk(Ask ask, Integer classify, MultipartFile[] files, List<String> tags);

    /**
     * 新增回复
     * @param answer
     * @param files
     * @param askId
     * @return
     */
    RespVO saveAnswer(Answer answer, MultipartFile[] files, String askId);

    /**
     * 查询提问列表
     * @param keyWords
     * @param tags
     * @param classify
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PagedResult<AskVo> list(String keyWords, List<String> tags, Integer classify, Integer pageIndex, Integer pageSize);

    /**
     * 查询提问详情
     * @param askId
     * @return
     */
    Knowledge details(String askId);

    /**
     * 我的提问
     * @return
     */
    List<AskVo> myAsk();

    /**
     * @param q
     * @param tag
     * @return
     */
    List<Knowledge> searchTag(String q, String tag);

    /**
     * @return
     */
    List<Knowledge> all();

    /**
     * @param id
     */
    void delete(String id);

    /**
     * @return
     */
    boolean drop();
}
