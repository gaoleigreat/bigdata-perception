package com.lego.perception.business.controller;
import com.lego.framework.base.annotation.Resource;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/3 18:02
 * @desc :
 */
@RestController
@RequestMapping("/business")
@Api(value = "business", description = "业务管理")
@Resource(value = "business", desc = "业务管理")
public class BusinessController {


}
