package com.lego.framework.system.feign;
import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.system.model.entity.Project;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/10/16 16:19
 * @desc :
 */
@FeignClient(value = "system-service", path = "/project",fallbackFactory = ProjectClientFallbackFactory.class)
public interface ProjectClient {

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    RespVO<Project> selectByPrimaryKey(@RequestParam(value = "id") Long id);


}

@Slf4j
@Component
class ProjectClientFallbackFactory implements FallbackFactory<ProjectClient>{

    @Override
    public ProjectClient create(Throwable throwable) {
        return id -> RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "system服务不可用");
    }
}
