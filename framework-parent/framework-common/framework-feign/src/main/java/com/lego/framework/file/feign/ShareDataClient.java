package com.lego.framework.file.feign;


import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.config.MultipartSupportConfig;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.framework.system.model.entity.ShareData;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author gaolei
 * @description
 * @since 2019/8/27
 **/
@FeignClient(value = "file-service", path = "/sharedata/v1", fallbackFactory = ShareDataClientFallbackFactory.class)
public interface ShareDataClient {

    /**
     * 通过批次号标签查询文件
     *
     * @param batchNums
     * @param tags      标签（多标签使用逗号隔开）
     * @return
     */
    @RequestMapping(value = "/selectByBatchNums", method = RequestMethod.GET)
    RespVO<RespDataVO<ShareData>> selectByBatchNums(@RequestParam(value = "bathNums") List<String> batchNums,
                                                    @RequestParam(required = false, value = "tags") String tags);


    /**
     * 批量新增
     *
     * @param batchNums
     * @param tags
     * @return
     */
    @RequestMapping(value = "/insertByBatchNums", method = RequestMethod.GET)
    RespVO insertByBatchNums(@RequestParam(value = "bathNums") List<String> batchNums,
                             @RequestParam(required = false, value = "tags") String tags);

    /**
     * 数据撤回共享
     *
     * @param batchNums
     * @param tags
     * @return
     */
    @RequestMapping(value = "/recallByBatchAndTags", method = RequestMethod.GET)
    RespVO recallByBatchAndTags(@RequestParam(value = "bathNums") List<String> batchNums,
                                @RequestParam(required = false, value = "tags") String tags);
}

@Slf4j
@Component
class ShareDataClientFallbackFactory implements FallbackFactory<ShareDataClient> {

    @Override
    public ShareDataClient create(Throwable throwable) {
        log.error("fallback; reason was:{}", throwable);
        return new ShareDataClient() {

            @Override
            public RespVO<RespDataVO<ShareData>> selectByBatchNums(List<String> bathNums, String tags) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file服务不可用");
            }

            @Override
            public RespVO insertByBatchNums(List<String> batchNums, String tags) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file服务不可用");
            }

            @Override
            public RespVO recallByBatchAndTags(List<String> batchNums, String tags) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file服务不可用");
            }

        };
    }
}



