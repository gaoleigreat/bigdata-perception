package com.lego.framework.business.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.SearchParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/25 19:43
 * @desc :
 */
@FeignClient(value = "business-service", path = "/curd", fallback = CrudClientFallback.class)
public interface CrudClient {

    /**
     * @param templateCode
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    RespVO createBusiness(@RequestParam(value = "templateCode") String templateCode);


    /**
     * @param templateCode
     * @param data
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    RespVO insertBusinessData(@RequestParam(value = "templateCode") String templateCode,
                              @RequestBody List<Map<String, Object>> data);


    /**
     * @param templateCode
     * @param searchParams
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    RespVO<RespDataVO<Map<String, Object>>> queryBusinessData(@RequestParam(value = "templateCode") String templateCode,
                                                              @RequestBody List<SearchParam> searchParams);


    /**
     * @param templateCode
     * @param searchParams
     * @param page
     * @return
     */
    @RequestMapping(value = "/queryPaged/{pageSize}/{pageIndex}", method = RequestMethod.POST)
    RespVO<PagedResult<Map>> queryPaged(@RequestParam(value = "templateCode") String templateCode,
                                        @RequestBody List<SearchParam> searchParams,
                                        @PathParam(value = "") Page page);


    /**
     * @param templateCode
     * @param data
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    RespVO updateBusinessData(@RequestParam(value = "templateCode") String templateCode,
                              @RequestBody Map<String, Object> data);


    /**
     * @param templateCode
     * @param data
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    RespVO delBusinessData(@RequestParam(value = "templateCode") String templateCode,
                           @RequestBody Map<String, Object> data);


    /**
     * @param templateCode
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    RespVO uploadBusinessData(@RequestParam(value = "templateCode") String templateCode,
                              @RequestParam(value = "file") MultipartFile file);


    /**
     * @param templateCode
     * @param searchParams
     * @param response
     * @return
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    RespVO downloadBusinessData(@RequestParam(value = "templateCode") String templateCode,
                                @RequestBody List<SearchParam> searchParams,
                                HttpServletResponse response);

}

@Component
class CrudClientFallback implements CrudClient {

    @Override
    public RespVO createBusiness(String templateCode) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
    }

    @Override
    public RespVO insertBusinessData(String templateCode, List<Map<String, Object>> data) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
    }

    @Override
    public RespVO<RespDataVO<Map<String, Object>>> queryBusinessData(String templateCode, List<SearchParam> searchParams) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
    }

    @Override
    public RespVO<PagedResult<Map>> queryPaged(String templateCode, List<SearchParam> searchParams, Page page) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
    }

    @Override
    public RespVO updateBusinessData(String templateCode, Map<String, Object> data) {
        return null;
    }

    @Override
    public RespVO delBusinessData(String templateCode, Map<String, Object> data) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
    }

    @Override
    public RespVO uploadBusinessData(String templateCode, MultipartFile file) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
    }

    @Override
    public RespVO downloadBusinessData(String templateCode, List<SearchParam> searchParams, HttpServletResponse response) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
    }
}
