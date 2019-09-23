package com.lego.framework.file.feign;


import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.file.model.UploadFile;
import com.lego.framework.system.model.entity.DataFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author gaolei
 * @description
 * @since 2019/8/27
 **/
@FeignClient(value = "file-service", path = "/datefile/v1", fallback = FileClientFallback.class)
public interface FileClient {

    @PostMapping(value = "/uploads", headers = "content-type=multipart/form-data")
    RespVO<RespDataVO<DataFile>> uploads(@RequestParam(value = "files", required = true) MultipartFile[] files,
                                         @RequestParam(value = "projectId", required = false) Long projectId,
                                         @RequestParam(value = "templateId", required = false) Long templateId,
                                         @RequestParam(value = "sourceType", required = true) int sourceType,
                                         @RequestParam(value = "remark", required = false) String remark,
                                         @RequestParam(value = "tags", required = false) String tags);

    @RequestMapping(value = "/selectByBatchNums", method = RequestMethod.GET)
    RespVO<RespDataVO<DataFile>> selectByBatchNums(@RequestParam(value = "bathNums") List<String> bathNums);
}

@Component
class FileClientFallback implements FileClient {


    @Override
    public RespVO<RespDataVO<DataFile>> uploads(MultipartFile[] files, Long projectId, Long templateId, int sourceType, String remark, String tags) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file-system服务不可用");
    }

    @Override
    public RespVO<RespDataVO<DataFile>> selectByBatchNums(List<String> bathNums) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file-system服务不可用");
    }
}



