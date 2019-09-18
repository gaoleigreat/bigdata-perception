package com.lego.framework.system.feign;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.framework.system.model.entity.Permission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/8/23
 **/
@FeignClient(value = "system-service", path = "/dataFile/v1", fallback = DataFileClientFallback.class)
public interface DataFileClient {


    /**
     * 添加文件
     *
     * @param dataFiles
     * @return
     */
    @RequestMapping(value = "/insertList", method = RequestMethod.POST)
    RespVO<RespDataVO<Long>> insertList(@RequestBody List<DataFile> dataFiles);

    /**
     * 添加文件
     *
     * @param dataFile
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    RespVO<Long> insert(@RequestBody DataFile dataFile);
}

@Component
class DataFileClientFallback implements DataFileClient {


    @Override
    public RespVO<RespDataVO<Long>> insertList(List<DataFile> dataFiles) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "system服务不可用");
    }


    @Override
    public RespVO<Long> insert(DataFile dataFile) {
        return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "system服务不可用");
    }
}
