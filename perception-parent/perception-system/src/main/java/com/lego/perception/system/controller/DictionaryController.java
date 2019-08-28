package com.lego.perception.system.controller;

import com.framework.common.consts.HttpConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.perception.system.init.DictionaryInit;
import com.lego.perception.system.service.IDictionaryService;
import com.lego.framework.system.model.entity.Dictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/dictionary/v1")
@Resource(value = "dictionary", desc = "字典管理")
@Api(value = "DictionaryController", description = "字典管理")
@Slf4j
public class DictionaryController {

    @Autowired
    private IDictionaryService dictionaryService;

    @Autowired
    private DictionaryInit dictionaryInit;

    @RequestMapping(value = "/getDictionByCode", method = RequestMethod.GET)
    @ApiOperation(value = "根据编码查询code", httpMethod = "GET")
    @ApiImplicitParams(
            {
            }
    )
    public Dictionary getDictionaryByCode(@RequestParam String code) {

        return dictionaryInit.getDictionaryMap().get(code);
    }

    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation(value = "查询列表，详情", httpMethod = "GET")
    public List<Dictionary> findList(@ModelAttribute Dictionary dictionary) {

        return dictionaryService.findList(dictionary);
    }

    @RequestMapping(value = "/findTree", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询树结构")
    public List<Dictionary> findTree(@ModelAttribute Dictionary dictionary, HttpServletRequest request) {
        String userId = request.getHeader(HttpConsts.USER_ID);
        String userName = request.getHeader(HttpConsts.USER_NAME);
        return dictionaryService.findTree(dictionary);
    }

    @RequestMapping(value = "/findPagedList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("分页查询")
    public PagedResult<Dictionary> findPagedList(@ModelAttribute Dictionary dictionary, @PathParam("") Page page) {

        return dictionaryService.findPagedList(dictionary, page);
    }

    @RequestMapping(value = "/insertList", method = RequestMethod.POST)
    @Operation(value = "insert", desc = "新增")
    @ApiOperation("批量新增")
    public RespVO insertList(@RequestBody List<Dictionary> dictionarys) {

        return dictionaryService.insertList(dictionarys);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(value = "insert", desc = "新增")
    @ApiOperation("新增")
    public RespVO insert(@RequestBody Dictionary dictionary) {

        return dictionaryService.insert(dictionary);
    }

    @RequestMapping(value = "/updateList", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("更新")
    public RespVO updateList(@RequestBody List<Dictionary> dictionarys) {

        return dictionaryService.updateList(dictionarys);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @Operation(value = "delete", desc = "删除")
    @ApiOperation("删除")
    public RespVO deleteList(@RequestParam Long id) {

        return dictionaryService.delete(id);
    }


    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    @Operation(value = "delete", desc = "删除")
    public RespVO deleteList(@RequestBody List<Long> ids) {

        return dictionaryService.deleteList(ids);
    }
}
