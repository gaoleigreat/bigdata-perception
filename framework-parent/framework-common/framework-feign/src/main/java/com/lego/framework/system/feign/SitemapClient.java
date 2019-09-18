package com.lego.framework.system.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.system.model.entity.Sitemap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/18 14:46
 * @desc :
 */
@FeignClient(value = "system-service", path = "sitemap", fallback = SitemapClientFallback.class)
public interface SitemapClient {


    /**
     * 新增菜单
     *
     * @param sitemap
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    RespVO insert(@RequestBody Sitemap sitemap);

}


@Component
class SitemapClientFallback implements SitemapClient {

    @Override
    public RespVO insert(Sitemap sitemap) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "system服务不可用");
    }
}