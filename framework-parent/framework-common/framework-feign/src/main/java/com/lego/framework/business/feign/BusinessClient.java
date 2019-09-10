package com.lego.framework.business.feign;

import com.framework.common.sdto.RespVO;
import com.lego.framework.template.model.entity.FormTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/10 10:19
 * @desc :
 */
@FeignClient(value = "business-service", path = "/business")
public interface BusinessClient {


    /**
     * @param formTemplate
     * @param sourceType
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    RespVO createBusiness(@RequestBody FormTemplate formTemplate,
                          @RequestParam(value = "sourceType") Integer sourceType);


    /**
     * @param formTemplate
     * @param data
     * @param sourceType
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    RespVO insertBusinessData(@RequestBody FormTemplate formTemplate,
                              @RequestParam(value = "data") List<Map<String, Object>> data,
                              @RequestParam(value = "sourceType") Integer sourceType);


}
