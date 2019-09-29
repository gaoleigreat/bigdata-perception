package com.lego.framework.business.feign;
import com.framework.common.consts.RespConsts;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.SearchParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/25 19:43
 * @desc :
 */
@FeignClient(value = "business-service", path = "/crud", fallback = CrudClientFallback.class)
public interface CrudClient {

    /**
     * 创建业务表
     *
     * @param templateCode
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    RespVO createBusiness(@RequestParam(value = "templateCode") String templateCode);


    /**
     * 新增业务数据
     *
     * @param templateCode
     * @param data
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    RespVO insertBusinessData(@RequestParam(value = "templateCode") String templateCode,
                              @RequestBody List<Map<String, Object>> data);


    /**
     * 查询业务数据
     *
     * @param templateCode
     * @param searchParams
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    RespVO<RespDataVO<Map<String, Object>>> queryBusinessData(@RequestParam(value = "templateCode") String templateCode,
                                                              @RequestBody List<SearchParam> searchParams);


    /**
     * 分页查询业务数据
     *
     * @param templateCode
     * @param searchParams
     * @param pageSize
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "/queryDataPaged/{pageSize}/{pageIndex}", method = RequestMethod.POST)
    RespVO<PagedResult<Map<String, Object>>> queryDataPaged(@RequestParam(value ="templateCode") String templateCode,
                                            @RequestBody List<SearchParam> searchParams,
                                            @PathVariable(value = "pageSize") Integer pageSize,
                                            @PathVariable(value = "pageIndex") Integer pageIndex);


    /**
     * 更新业务数据
     *
     * @param templateCode
     * @param data
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    RespVO updateBusinessData(@RequestParam(value = "templateCode") String templateCode,
                              @RequestBody Map<String, Object> data);


    /**
     * 删除业务数据
     *
     * @param templateCode
     * @param data
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    RespVO delBusinessData(@RequestParam(value = "templateCode") String templateCode,
                           @RequestBody Map<String, Object> data);


    /**
     * 上传业务数据
     *
     * @param templateCode
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    RespVO uploadBusinessData(@RequestParam(value = "templateCode") String templateCode,
                              @RequestParam(value = "file") MultipartFile file);


    /**
     * 下载业务数据
     *
     * @param templateCode
     * @param searchParams
     * @param response
     * @return
     */
/*    @RequestMapping(value = "/download", method = RequestMethod.POST)
    RespVO downloadBusinessData(@RequestParam(value = "templateCode") String templateCode,
                                @RequestBody List<SearchParam> searchParams,
                                HttpServletResponse response);*/

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
    public RespVO<PagedResult<Map<String, Object>>> queryDataPaged(String templateCode, List<SearchParam> searchParams, Integer pageSize, Integer pageIndex) {
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

    /*@Override
    public RespVO downloadBusinessData(String templateCode, List<SearchParam> searchParams, HttpServletResponse response) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
    }*/
}
