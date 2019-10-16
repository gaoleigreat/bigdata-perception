package com.lego.framework.template.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.DataTemplate;
import com.lego.framework.template.model.entity.EnumerationItem;
import com.lego.framework.template.model.entity.FormTemplate;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author yanglf
 */
@FeignClient(value = "template-service", fallbackFactory = TemplateFallBackFactory.class)
public interface TemplateFeignClient {

    /**
     * 根据枚举id查询枚举信息
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/enumeration/v1/find/{id}", method = RequestMethod.GET)
    RespVO<Enumeration> findEnumerationById(@PathVariable(value = "id") String code);


    /**
     * 查询枚举值
     *
     * @param enumId
     * @param value
     * @return
     */
    @RequestMapping(value = "/enumeration/v1/findItem/{enumId}/{value}", method = RequestMethod.GET)
    RespVO<EnumerationItem> findItem(@PathVariable(value = "enumId") Long enumId, @PathVariable(value = "value") Integer value);

    /**
     * 查询枚举值列表
     *
     * @param enumerationItem
     * @return
     */
    @RequestMapping(value = "/enumeration/v1/findItemList", method = RequestMethod.POST)
    RespVO<List<EnumerationItem>> findItemList(@RequestBody EnumerationItem enumerationItem);

    /**
     * @param enumId
     * @param label
     * @return
     */
    @RequestMapping(value = "/enumeration/v1/findItemLable/{enumId}/{label}", method = RequestMethod.GET)
    RespVO<EnumerationItem> findItemLable(@PathVariable(value = "enumId") Long enumId,
                                          @PathVariable(value = "label") String label);

    /**
     * @param map
     * @return
     */
    @RequestMapping(value = "/enumeration/v1/findEnumerationList", method = RequestMethod.POST)
    RespVO<Map<Long, String>> findEnumerationList(@RequestBody Map<Long, Integer> map);


    /**
     * 根据枚举cde查询枚举信息
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/enumeration/v1/findByCode/{code}", method = RequestMethod.GET)
    RespVO<Enumeration> findEnumerationByCode(@PathVariable(value = "code") String code);

    /**
     * 根据id查询数据模板
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/dataTemplate/v1/find/{id}", method = RequestMethod.GET)
    RespVO<DataTemplate> findDataTemplateById(@PathVariable(value = "id") Long id);

    /**
     * 根据code查询数据模板
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/dataTemplate/v1/findByCode/{code}", method = RequestMethod.GET)
    RespVO<DataTemplate> findDataTemplateByCode(@PathVariable(value = "code") String code);


    /**
     * 根据id查询表单模板
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/formTemplate/v1/find/{id}", method = RequestMethod.GET)
    RespVO<FormTemplate> findFormTemplateById(@PathVariable(value = "id") Long id);

    /**
     * 根据code查询表单模板
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/formTemplate/v1/findByCode/{code}", method = RequestMethod.GET)
    RespVO<FormTemplate> findFormTemplateByCode(@PathVariable(value = "code") String code);


    /**
     * 查询formTemplate 列表
     *
     * @param dataType
     * @return
     */
    @RequestMapping(value = "/formTemplate/v1/findByDataType/{dataType}", method = RequestMethod.GET)
    RespVO<FormTemplate> findByDataType(@PathVariable(value = "dataType") Integer dataType);


}

@Slf4j
@Component
class TemplateFallBackFactory implements FallbackFactory<TemplateFeignClient> {

    @Override
    public TemplateFeignClient create(Throwable throwable) {
        log.error("fallback; reason was:", throwable);
        return new TemplateFeignClient() {
            @Override
            public RespVO<Enumeration> findEnumerationById(String code) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "template服务不可用");
            }

            @Override
            public RespVO<EnumerationItem> findItem(Long enumId, Integer value) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "template服务不可用");
            }

            @Override
            public RespVO<List<EnumerationItem>> findItemList(EnumerationItem enumerationItem) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "template服务不可用");
            }

            @Override
            public RespVO<EnumerationItem> findItemLable(Long enumId, String label) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "template服务不可用");
            }

            @Override
            public RespVO<Map<Long, String>> findEnumerationList(Map<Long, Integer> map) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "template服务不可用");
            }

            @Override
            public RespVO<Enumeration> findEnumerationByCode(String code) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "template服务不可用");
            }

            @Override
            public RespVO<DataTemplate> findDataTemplateById(Long id) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "template服务不可用");
            }

            @Override
            public RespVO<DataTemplate> findDataTemplateByCode(String code) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "template服务不可用");
            }

            @Override
            public RespVO<FormTemplate> findFormTemplateById(Long id) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "template服务不可用");
            }

            @Override
            public RespVO<FormTemplate> findFormTemplateByCode(String code) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "template服务不可用");
            }

            @Override
            public RespVO<FormTemplate> findByDataType(Integer dataType) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "template服务不可用");
            }
        };
    }
}

