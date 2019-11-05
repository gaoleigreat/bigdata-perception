package com.lego.framework.file.feign;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.config.MultipartSupportConfig;
import com.lego.framework.file.model.PerceptionFile;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;;

import java.util.List;

@FeignClient(value = "file-service", path = "/perceptionFile", fallbackFactory = PerceptionFileClientFallback.class, configuration = MultipartSupportConfig.class)
public interface PerceptionFileClient {


    @GetMapping("/{id}")
    RespVO<PerceptionFile> selectByPrimaryKey(@PathVariable(value = "id") Long id);


    @DeleteMapping("/{id}")
    RespVO deleteByPrimaryKey(@PathVariable(value = "id") Long id);


    @PostMapping("/")
    RespVO insert(@RequestBody PerceptionFile perceptionFile);


    @PutMapping("/")
    RespVO updateByPrimaryKey(@RequestBody PerceptionFile perceptionFile);


    @DeleteMapping("/deleteBatchPrimaryKeys")
    RespVO deleteBatchPrimaryKeys(@RequestBody List<Long> list);


    @PostMapping("/list")
    RespVO<RespDataVO<PerceptionFile>>query(@RequestBody PerceptionFile perceptionFile);


    @PostMapping(value = "/upLoad", headers = "content-type=multipart/form-data", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RespVO<RespDataVO<PerceptionFile>> upload(@RequestPart(value = "files") MultipartFile[] files,
                                              @RequestParam(value = "businessModule") String businessModule,
                                              @RequestParam(value = "projectId", required = false) Long projectId,
                                              @RequestParam(value = "remark") String remark,
                                              @RequestParam(value = "tags", required = false) String tags,
                                              @RequestParam(value = "createBy") String createBy,
                                              @RequestParam(value = "isStructured", required = false, defaultValue = "1") int isStructured
    );
}
@Component
class PerceptionFileClientFallback implements FallbackFactory<PerceptionFileClient> {


    @Override
    public PerceptionFileClient create(Throwable throwable) {
        return new PerceptionFileClient() {
            @Override
            public RespVO<PerceptionFile> selectByPrimaryKey(Long id) {
                return RespVOBuilder.failure("file-service服务不可用");
            }


            @Override
            public RespVO deleteByPrimaryKey(Long id) {
                return RespVOBuilder.failure("file-service服务不可用");
            }


            @Override
            public RespVO insert(@RequestBody PerceptionFile perceptionFile) {
                return RespVOBuilder.failure("file-service服务不可用");
            }


            @Override
            public RespVO updateByPrimaryKey(PerceptionFile perceptionFile) {
                return RespVOBuilder.failure("file-service服务不可用");
            }


            @Override
            public RespVO deleteBatchPrimaryKeys(List<Long> list) {
                return RespVOBuilder.failure("file-service服务不可用");
            }


            @Override
            public RespVO<RespDataVO<PerceptionFile>> query(PerceptionFile perceptionFile) {
                return RespVOBuilder.failure("model-service服务不可用");
            }

            @Override
            public RespVO<RespDataVO<PerceptionFile>> upload(MultipartFile[] files, String businessModule, Long
                    projectId, String remark, String tags, String createBy, int isStructured) {
                return RespVOBuilder.failure("file-service服务不可用");
            }
        };
    }
}
