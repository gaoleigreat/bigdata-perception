package com.lego.perception.system.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.system.model.entity.TbSystem;
import com.lego.perception.system.service.SystemService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-03 02:38:11
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private SystemService systemService;

    /**
     * 分页查询数据
     */
    @RequestMapping(value = "/findPaged/{pageSize}/{pageIndex}",method = RequestMethod.GET)
    public RespVO<PagedResult<TbSystem>> selectPaged(@ModelAttribute TbSystem tbSystem, @PathParam(value = "")Page page) {
        PagedResult<TbSystem> result = systemService.selectPaged(tbSystem,page);
        return RespVOBuilder.success(result);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @RequestMapping("/select_by_id")
    public RespVO<TbSystem> selectByPrimaryKey(Long id) {
        TbSystem po = systemService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @RequestMapping("/delete_by_id")
    public RespVO deleteByPrimaryKey(Long id) {
        Integer num = systemService.deleteByPrimaryKey(id);
        return RespVOBuilder.success();
    }

    /**
     * 新增数据
     *
     * @return
     */
    @RequestMapping("/save_system")
    public RespVO insert(TbSystem system) {
        Integer num = systemService.insertSelective(system);
        return RespVOBuilder.success();
    }

    /**
     * 修改数据
     *
     * @return
     */
    @RequestMapping("/update_system")
    public RespVO updateByPrimaryKeySelective(TbSystem system) {
        Integer num = systemService.updateByPrimaryKeySelective(system);
        return RespVOBuilder.success();
    }


    /**
     * 查询列表
     *
     * @return
     */
    @RequestMapping("/query_list")
    public RespVO<RespDataVO<TbSystem>> queryByCondition(TbSystem system) {
        List<TbSystem> list = systemService.query(system);
        return RespVOBuilder.success(list);
    }

}
