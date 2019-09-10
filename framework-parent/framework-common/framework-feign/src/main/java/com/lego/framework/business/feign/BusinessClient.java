package com.lego.framework.business.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.FormTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @auther xiaodao
 * @date 2019/9/9 16:44
 */

@FeignClient(value = "system-business", path = "/business")
public interface BusinessClient {
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
     RespVO insert(@RequestBody FormTemplate formTemplate,
                         @RequestBody List<Map<String, Object>> data,
                         @RequestParam(value = "sourceType") Integer sourceType);

}
@Component
class BusinessClientFallback implements BusinessClient {


    @Override
    public RespVO insert(FormTemplate formTemplate, List<Map<String, Object>> data, Integer sourceType) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "busuness服务不可用");
    }
}