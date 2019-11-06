package com.lego.framework.data.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.config.MultipartSupportConfig;
import com.lego.framework.data.model.entity.PerceptionUnstructuredData;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@FeignClient(value = "data-service", path = "/perceptionUnstructuredData", fallbackFactory = PerceptionUnstructuredDataClientFallback.class, configuration = MultipartSupportConfig.class)
public interface PerceptionUnstructuredDataClient {


    @GetMapping("/{id}")
    RespVO<PerceptionUnstructuredData> selectByPrimaryKey(@PathVariable(value = "id") Long id);


    @DeleteMapping("/{id}")
    RespVO deleteByPrimaryKey(@PathVariable(value = "id") Long id);


    @PostMapping("/")
    RespVO insert(@RequestBody PerceptionUnstructuredData perceptionUnstructuredData);


    @PutMapping("/")
    RespVO updateByPrimaryKey(@RequestBody PerceptionUnstructuredData perceptionUnstructuredData);


    @DeleteMapping("/deleteBatchPrimaryKeys")
    RespVO deleteBatchPrimaryKeys(@RequestBody List<Long> list);


    @PostMapping("/list")
    RespVO<RespDataVO<PerceptionUnstructuredData>> query(@RequestBody PerceptionUnstructuredData perceptionUnstructuredData);
}

@Component
class PerceptionUnstructuredDataClientFallback implements FallbackFactory<PerceptionUnstructuredDataClient> {


    @Override
    public PerceptionUnstructuredDataClient create(Throwable throwable) {
        return new PerceptionUnstructuredDataClient() {

            @Override
            public RespVO<PerceptionUnstructuredData> selectByPrimaryKey(Long id) {
                return RespVOBuilder.failure("file-service服务不可用");
            }


            @Override
            public RespVO deleteByPrimaryKey(Long id) {
                return RespVOBuilder.failure("file-service服务不可用");
            }


            @Override
            public RespVO insert(@RequestBody PerceptionUnstructuredData perceptionUnstructuredData) {
                return RespVOBuilder.failure("file-service服务不可用");
            }


            @Override
            public RespVO updateByPrimaryKey(PerceptionUnstructuredData perceptionUnstructuredData) {
                return RespVOBuilder.failure("file-service服务不可用");
            }


            @Override
            public RespVO deleteBatchPrimaryKeys(List<Long> list) {
                return RespVOBuilder.failure("file-service服务不可用");
            }


            @Override
            public RespVO<RespDataVO<PerceptionUnstructuredData>> query(PerceptionUnstructuredData perceptionUnstructuredData) {
                return RespVOBuilder.failure("file-service服务不可用");
            }
        };
    }
}