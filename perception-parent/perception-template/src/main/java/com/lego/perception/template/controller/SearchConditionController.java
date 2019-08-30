package com.lego.perception.template.controller;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.template.model.entity.ConditionSymbol;
import com.lego.framework.template.model.entity.DataTemplateItem;
import com.lego.perception.template.service.ISearchConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/searchCondition/v1")
@Api(tags="searchCondition",description = "高级搜索条件")
@Resource(value = "searchCondition", desc = "搜索条件")
@Slf4j
public class SearchConditionController {

    @Autowired
    private ISearchConditionService searchConditionService;

    @RequestMapping(value = "/newHousehold/{templateCode}", method = RequestMethod.GET)
    @ApiOperation("新农户")
    public RespVO<Map<String, Object>> newHousehold(@PathVariable("templateCode") String templateCode){
        List<DataTemplateItem> items = searchConditionService.findSearchCondition(templateCode);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", items);
        map.put("symbols", getSymbols());
        return RespVOBuilder.success(map);
    }

    @RequestMapping(value = "/household/{templateCode}", method = RequestMethod.GET)
    @ApiOperation("户档")
    public RespVO<Map<String, Object>> household(@PathVariable("templateCode") String templateCode){
        List<DataTemplateItem> items = searchConditionService.findSearchCondition(templateCode);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", items);
        map.put("symbols", getSymbols());
        return RespVOBuilder.success(map);
    }

    private Map<Integer, List<ConditionSymbol>> getSymbols(){
        List<ConditionSymbol> enumList = Arrays.asList(new ConditionSymbol[]{new ConditionSymbol("包含","in"),
                new ConditionSymbol("不包含","notin"),
                new ConditionSymbol("为空","notExists"),
                new ConditionSymbol("不为空","exists")});
        List<ConditionSymbol> numList = Arrays.asList(new ConditionSymbol[]{new ConditionSymbol("等于","="),
                new ConditionSymbol("大于",">"),
                new ConditionSymbol("大于等于",">="),
                new ConditionSymbol("小于","<"),
                new ConditionSymbol("小于等于", "<=")});
        List<ConditionSymbol> stringList = Arrays.asList(new ConditionSymbol[]{new ConditionSymbol("包含","like"),
                new ConditionSymbol("等于","="),
                new ConditionSymbol("为空","notExists"),
                new ConditionSymbol("不为空","exists")});
        List<ConditionSymbol> booleanList = Arrays.asList(new ConditionSymbol[]{new ConditionSymbol("是","true"),
                new ConditionSymbol("否","false"),
                new ConditionSymbol("为空","notExists"),
                new ConditionSymbol("不为空","exists")});
        Map<Integer, List<ConditionSymbol>> map = new HashMap<>();
        map.put(1, stringList);
        map.put(2, stringList);
        map.put(3,null);
        map.put(4,null);
        map.put(5,null);
        map.put(6,enumList);
        map.put(7,enumList);
        map.put(8,null);
        map.put(9,numList);
        map.put(10,null);
        map.put(11,null);
        map.put(13, booleanList);
        map.put(14,numList);
        map.put(15,stringList);
        map.put(16,null);
        map.put(17,null);
       return map;
    }
}
