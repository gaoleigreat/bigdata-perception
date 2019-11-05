package com.lego.framework.data.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.config.MultipartSupportConfig;
import com.lego.framework.data.model.entity.PerceptionStructuredData;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "data-service", path = "/perceptionStructuredData", fallbackFactory = PerceptionStructuredDataClientFallback.class, configuration = MultipartSupportConfig.class)
public interface PerceptionStructuredDataClient {


    @GetMapping("/{id}")
    RespVO<PerceptionStructuredData> selectByPrimaryKey(@PathVariable(value = "id") Long id);


    @DeleteMapping("/{id}")
    RespVO deleteByPrimaryKey(@PathVariable(value = "id") Long id);


    @PostMapping("/")
    RespVO insert(@RequestBody PerceptionStructuredData perceptionStructuredData);


    @PutMapping("/")
    RespVO updateByPrimaryKey(@RequestBody PerceptionStructuredData perceptionStructuredData);


    @DeleteMapping("/deleteBatchPrimaryKeys")
    RespVO deleteBatchPrimaryKeys(@RequestBody List<Long> list);


    @PostMapping("/list")
    RespVO<RespDataVO<PerceptionStructuredData>> query(@RequestBody PerceptionStructuredData perceptionStructuredData);
}

@Component
class PerceptionStructuredDataClientFallback implements FallbackFactory<PerceptionStructuredDataClient> {


    @Override
    public PerceptionStructuredDataClient create(Throwable throwable) {
        return new PerceptionStructuredDataClient() {


            @Override
            public RespVO<PerceptionStructuredData> selectByPrimaryKey(Long id) {
                return RespVOBuilder.failure("data-service服务不可用");
            }


            @Override
            public RespVO deleteByPrimaryKey(Long id) {
                return RespVOBuilder.failure("data-service服务不可用");
            }


            @Override
            public RespVO insert(@RequestBody PerceptionStructuredData perceptionStructuredData) {
                return RespVOBuilder.failure("data-service服务不可用");
            }


            @Override
            public RespVO updateByPrimaryKey(PerceptionStructuredData perceptionStructuredData) {
                return RespVOBuilder.failure("data-service服务不可用");
            }


            @Override
            public RespVO deleteBatchPrimaryKeys(List<Long> list) {
                return RespVOBuilder.failure("data-service服务不可用");
            }


            @Override
            public RespVO<RespDataVO<PerceptionStructuredData>> query(PerceptionStructuredData perceptionStructuredData) {
                return RespVOBuilder.failure("data-service服务不可用");
            }
        };
    }
}