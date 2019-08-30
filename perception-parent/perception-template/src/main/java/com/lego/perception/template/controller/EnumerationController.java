package com.lego.perception.template.controller;
import com.baomidou.mybatisplus.extension.api.R;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.template.model.entity.*;
import com.lego.framework.template.model.entity.Enumeration;
import com.lego.perception.template.init.EnumerationInit;
import com.lego.perception.template.service.IDataTemplateService;
import com.lego.perception.template.service.IEnumerationItemService;
import com.lego.perception.template.service.IEnumerationService;
import com.lego.perception.template.service.IFormTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;

@RestController
@RequestMapping("/enumeration/v1")
@Resource(value = "enumeration", desc = "枚举管理")
@Api(tags = "枚举管理")
@Slf4j
public class EnumerationController {

    @Autowired
    private IEnumerationService enumerationService;


    @Autowired
    private IEnumerationItemService iEnumerationItemService;

    @Autowired
    private EnumerationInit enumerationInit;

    @Autowired
    private IFormTemplateService iFormTemplateService;

    @Autowired
    private IDataTemplateService iDataTemplateService;

    @RequestMapping(value = "/findPagedList/{pageSize}/{curPage}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "curPage", value = "请求页", dataType = "int", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataType = "int", required = true, paramType = "query"),
    })
    @ApiOperation("分页查询枚举")
    public RespVO<PagedResult<Enumeration>> findPagedList(@ModelAttribute Enumeration enumeration, @PathParam("") Page page) {

        return RespVOBuilder.success(enumerationService.findPagedList(enumeration, page));
    }


    @RequestMapping(value = "/findCybfList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询产业帮扶细分")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "0-产业帮扶措施;1-产业扶持类型", dataType = "int", required = true, paramType = "query"),
    })
    public RespVO<EnumerationTreeVo> findCybfList(int type) {
        EnumerationTreeVo cyfcTreeVo;
        if (type == 1) {
            // 产业扶持
            cyfcTreeVo = new EnumerationTreeVo(133L, 0, "产业扶持类型");
            List<EnumerationTreeVo> cyChildTreeVos = getCyfcChildList();
            cyfcTreeVo.setEnumerationTreeVos(cyChildTreeVos);
        } else {
            // 产业帮扶措施
            cyfcTreeVo = new EnumerationTreeVo(192L, 0, "产业帮扶措施");
            List<EnumerationTreeVo> cyChildTreeVos = getCybfcsChildList();
            cyfcTreeVo.setEnumerationTreeVos(cyChildTreeVos);
        }
        return RespVOBuilder.success(cyfcTreeVo);
    }

    /**
     * 产业
     * @return
     */
    private List<EnumerationTreeVo> getCybfcsChildList() {
        List<EnumerationTreeVo> cybfcsChildList=new ArrayList<>();

        // 项目直补
        EnumerationTreeVo xmzbTreeVo = new EnumerationTreeVo(192L, 1, "项目直补");
        cybfcsChildList.add(xmzbTreeVo);

        // 集体经济
        EnumerationTreeVo jtjjTreeVo = new EnumerationTreeVo(192L, 2, "集体经济");
        jtjjTreeVo.setEnumerationTreeVos(childList(183L));
        cybfcsChildList.add(jtjjTreeVo);



        //TODO 科技帮扶  不联动
        EnumerationTreeVo kjbfTreeVo = new EnumerationTreeVo(192L, 4, "科技帮扶");
        kjbfTreeVo.setEnumerationTreeVos(getKjbfms());
        cybfcsChildList.add(kjbfTreeVo);

        // 金融支持
        EnumerationTreeVo jrzcTreeVo = new EnumerationTreeVo(192L, 5, "金融支持");
        jrzcTreeVo.setEnumerationTreeVos(childList(228L));
        cybfcsChildList.add(jrzcTreeVo);

        //TODO 主体带动   ----  不联动
        EnumerationTreeVo ztddTreeVo = new EnumerationTreeVo(192L, 3, "主体带动");
        ztddTreeVo.setEnumerationTreeVos(getZtlxList());
        cybfcsChildList.add(ztddTreeVo);

        return cybfcsChildList;
    }

    /**
     * 主体类型
     * @return
     */
    private List<EnumerationTreeVo> getZtlxList() {
        List<EnumerationTreeVo> ztlxList=new ArrayList<>();

        EnumerationTreeVo ztddQy=new EnumerationTreeVo(184L,1,"企业");
        ztddQy.setEnumerationTreeVos(getZtgmList());
        ztlxList.add(ztddQy);

        EnumerationTreeVo jtnc=new EnumerationTreeVo(184L,2,"家庭农场");
        jtnc.setEnumerationTreeVos(getZtgmList());
        ztlxList.add(jtnc);

        EnumerationTreeVo nyyq=new EnumerationTreeVo(184L,3,"农业园区");
        nyyq.setEnumerationTreeVos(getZtgmList());
        ztlxList.add(nyyq);

        EnumerationTreeVo hzs=new EnumerationTreeVo(184L,4,"合作社");
        hzs.setEnumerationTreeVos(getZtgmList());
        ztlxList.add(hzs);

        EnumerationTreeVo zydh=new EnumerationTreeVo(184L,5,"种养大户");
        zydh.setEnumerationTreeVos(getZtgmList());
        ztlxList.add(zydh);

        EnumerationTreeVo jtjjdd=new EnumerationTreeVo(184L,6,"村集体经济项目带动");
        jtjjdd.setEnumerationTreeVos(getZtgmList());
        ztlxList.add(jtjjdd);

        EnumerationTreeVo qt=new EnumerationTreeVo(184L,7,"其他");
        qt.setEnumerationTreeVos(getZtgmList());
        ztlxList.add(qt);

        return ztlxList;
    }

    /**
     * 主体规模
     * @return
     */
    private List<EnumerationTreeVo> getZtgmList() {
        List<EnumerationTreeVo> ztgmList=new ArrayList<>();

        EnumerationTreeVo guojiaji=new EnumerationTreeVo(185L,1,"国家级");
        guojiaji.setEnumerationTreeVos(getDaidongjizhi());
        ztgmList.add(guojiaji);

        EnumerationTreeVo sengji=new EnumerationTreeVo(185L,2,"省级");
        sengji.setEnumerationTreeVos(getDaidongjizhi());
        ztgmList.add(sengji);

        EnumerationTreeVo shiji=new EnumerationTreeVo(185L,3,"市级");
        shiji.setEnumerationTreeVos(getDaidongjizhi());
        ztgmList.add(shiji);

        EnumerationTreeVo xianji=new EnumerationTreeVo(185L,4,"县级");
        xianji.setEnumerationTreeVos(getDaidongjizhi());
        ztgmList.add(xianji);

        EnumerationTreeVo qita=new EnumerationTreeVo(185L,5,"其他");
        qita.setEnumerationTreeVos(getDaidongjizhi());
        ztgmList.add(qita);

        return ztgmList;
    }

    /**
     * 带动机制
     * @return
     */
    private List<EnumerationTreeVo> getDaidongjizhi() {
        List<EnumerationTreeVo> daidongjizhiList=new ArrayList<>();



        EnumerationTreeVo tudiliuzhuan=new EnumerationTreeVo(186L,1,"土地流转");
        List<EnumerationTreeVo> enumerationTreeVos = childList(187L);
        tudiliuzhuan.setEnumerationTreeVos(enumerationTreeVos);
        daidongjizhiList.add(tudiliuzhuan);


        EnumerationTreeVo dingdansc=new EnumerationTreeVo(186L,2,"订单生产");
        dingdansc.setEnumerationTreeVos(enumerationTreeVos);
        daidongjizhiList.add(dingdansc);

        EnumerationTreeVo shehuihfw=new EnumerationTreeVo(186L,3,"社会化服务");
        shehuihfw.setEnumerationTreeVos(enumerationTreeVos);
        daidongjizhiList.add(shehuihfw);

        EnumerationTreeVo fanzudaobao=new EnumerationTreeVo(186L,4,"返租倒包");
        fanzudaobao.setEnumerationTreeVos(enumerationTreeVos);
        daidongjizhiList.add(fanzudaobao);

        EnumerationTreeVo tuoguanjy=new EnumerationTreeVo(186L,5,"托管经营");
        tuoguanjy.setEnumerationTreeVos(enumerationTreeVos);
        daidongjizhiList.add(tuoguanjy);

        EnumerationTreeVo ruguly=new EnumerationTreeVo(186L,6,"入股联营");
        ruguly.setEnumerationTreeVos(enumerationTreeVos);
        daidongjizhiList.add(ruguly);

        EnumerationTreeVo zhegulh=new EnumerationTreeVo(186L,7,"折股量化");
        zhegulh.setEnumerationTreeVos(enumerationTreeVos);
        daidongjizhiList.add(zhegulh);

        EnumerationTreeVo qita=new EnumerationTreeVo(186L,8,"其他");
        qita.setEnumerationTreeVos(enumerationTreeVos);
        daidongjizhiList.add(qita);

        return daidongjizhiList;
    }

    /**
     * 科技帮扶模式
     * @return
     */
    private List<EnumerationTreeVo> getKjbfms() {
        List<EnumerationTreeVo> kjbfmsList=new ArrayList<>();

        EnumerationTreeVo kj110=new EnumerationTreeVo(188L,1,"科技110");
        kj110.setEnumerationTreeVos(getJsbfcs());
        kjbfmsList.add(kj110);

        EnumerationTreeVo jspx=new EnumerationTreeVo(188L,2,"技术培训");
        jspx.setEnumerationTreeVos(getJsbfcs());
        kjbfmsList.add(jspx);

        EnumerationTreeVo jsjdbf=new EnumerationTreeVo(188L,3,"技术结对帮扶");
        jsjdbf.setEnumerationTreeVos(getJsbfcs());
        kjbfmsList.add(jsjdbf);

        EnumerationTreeVo qt=new EnumerationTreeVo(188L,4,"其他");
        qt.setEnumerationTreeVos(getJsbfcs());
        kjbfmsList.add(qt);

        return kjbfmsList;
    }

    private List<EnumerationTreeVo> getJsbfcs() {
        List<EnumerationTreeVo> jsbfcsList=new ArrayList<>();

        EnumerationTreeVo one=new EnumerationTreeVo(189L,1,"1");
        List<EnumerationTreeVo> enumerationTreeVos = childList(190L);
        one.setEnumerationTreeVos(enumerationTreeVos);
        jsbfcsList.add(one);

        EnumerationTreeVo two=new EnumerationTreeVo(189L,2,"2");
        two.setEnumerationTreeVos(enumerationTreeVos);
        jsbfcsList.add(two);


        EnumerationTreeVo three=new EnumerationTreeVo(189L,3,"3");
        three.setEnumerationTreeVos(enumerationTreeVos);
        jsbfcsList.add(three);

        EnumerationTreeVo four=new EnumerationTreeVo(189L,4,"4");
        four.setEnumerationTreeVos(enumerationTreeVos);
        jsbfcsList.add(four);

        return jsbfcsList;
    }



    private List<EnumerationTreeVo> getCyfcChildList() {
        List<EnumerationTreeVo> cyChildTreeVos = new ArrayList<>();
        // 种植业
        EnumerationTreeVo zzyTreeVo = new EnumerationTreeVo(133L, 1, "种植业");
        zzyTreeVo.setEnumerationTreeVos(getZzyChildList());
        cyChildTreeVos.add(zzyTreeVo);

        // 养殖业
        EnumerationTreeVo yzyTreeVo = new EnumerationTreeVo(133L, 2, "养殖业");
        //TODO 养殖业统计
        yzyTreeVo.setEnumerationTreeVos(childList(171L));
        cyChildTreeVos.add(yzyTreeVo);

        // 林业
        EnumerationTreeVo lyTreeVo = new EnumerationTreeVo(133L, 3, "林业");
        lyTreeVo.setEnumerationTreeVos(getLyChildList());
        cyChildTreeVos.add(lyTreeVo);

        //渔业
        EnumerationTreeVo yyTreeVo = new EnumerationTreeVo(133L, 4, "渔业");
        yyTreeVo.setEnumerationTreeVos(childList(176L));
        cyChildTreeVos.add(yyTreeVo);

        // 旅游
        EnumerationTreeVo lyyTreeVo = new EnumerationTreeVo(133L, 5, "旅游业");
        lyyTreeVo.setEnumerationTreeVos(childList(177L));
        cyChildTreeVos.add(lyyTreeVo);

        // TODO 光伏  不级联
        EnumerationTreeVo gfTreeVo = new EnumerationTreeVo(133L, 6, "光伏发电");
        gfTreeVo.setEnumerationTreeVos(getGfChildList());
        cyChildTreeVos.add(gfTreeVo);

        //加工
        EnumerationTreeVo jgTreeVo = new EnumerationTreeVo(133L, 7, "加工业");
        jgTreeVo.setEnumerationTreeVos(childList(179L));
        cyChildTreeVos.add(jgTreeVo);
        //电商
        EnumerationTreeVo dsTreeVo = new EnumerationTreeVo(133L, 8, "电商");
        dsTreeVo.setEnumerationTreeVos(childList(181L));
        cyChildTreeVos.add(dsTreeVo);

        //其他产业
        EnumerationTreeVo qtTreeVo = new EnumerationTreeVo(133L, 9, "其他产业");
        qtTreeVo.setEnumerationTreeVos(childList(180L));
        cyChildTreeVos.add(qtTreeVo);

        return cyChildTreeVos;
    }


    /**
     * 获取光伏子项
     *
     * @return
     */
    private List<EnumerationTreeVo> getGfChildList() {
        List<EnumerationTreeVo> gfChildList = new ArrayList<>();

        EnumerationTreeVo zczz = new EnumerationTreeVo(211L, 1, "自筹资金");
        zczz.setEnumerationTreeVos(getTzlyChildList());
        gfChildList.add(zczz);

        EnumerationTreeVo dkzz = new EnumerationTreeVo(211L, 2, "贷款资金");
        dkzz.setEnumerationTreeVos(getTzlyChildList());
        gfChildList.add(dkzz);

        return gfChildList;
    }

    /**
     * 投资来源子项
     *
     * @return
     */
    private List<EnumerationTreeVo> getTzlyChildList() {
        // 枚举值
        List<EnumerationTreeVo> tzly = new ArrayList<>();
        EnumerationTreeVo zcjs = new EnumerationTreeVo(226L, 1, "自筹建设");
        List<EnumerationTreeVo> enumerationTreeVos = childList(227L);
        zcjs.setEnumerationTreeVos(enumerationTreeVos);
        tzly.add(zcjs);
        EnumerationTreeVo wtjs = new EnumerationTreeVo(226L, 2, "委托建设");
        wtjs.setEnumerationTreeVos(enumerationTreeVos);
        tzly.add(wtjs);
        return tzly;
    }


    /**
     * 获取林业子项
     *
     * @return
     */
    private List<EnumerationTreeVo> getLyChildList() {
        List<EnumerationTreeVo> lyChildTreeVos = new ArrayList<>();
        // 特色林业
        EnumerationTreeVo tslyTreeVo = new EnumerationTreeVo(172L, 1, "特色林产业");
        tslyTreeVo.setEnumerationTreeVos(childList(173L));
        lyChildTreeVos.add(tslyTreeVo);

        //林下种植业
        EnumerationTreeVo lxzzyTreeVo = new EnumerationTreeVo(172L, 2, "林下种植业");
        lxzzyTreeVo.setEnumerationTreeVos(childList(175L));
        lyChildTreeVos.add(lxzzyTreeVo);

        EnumerationTreeVo zzhhTreeVo = new EnumerationTreeVo(172L, 3, "林类种苗花卉");
        lyChildTreeVos.add(zzhhTreeVo);

        // 林下养殖业
        EnumerationTreeVo lxyzyTreeVo = new EnumerationTreeVo(172L, 4, "林下养殖业");
        lxzzyTreeVo.setEnumerationTreeVos(childList(174L));
        lyChildTreeVos.add(lxyzyTreeVo);

        return lyChildTreeVos;
    }


    /**
     * 种植业子项
     *
     * @return
     */
    private List<EnumerationTreeVo> getZzyChildList() {
        List<EnumerationTreeVo> zzyChildList = new ArrayList<>();
        // 谷物
        EnumerationTreeVo gwTreeVo = new EnumerationTreeVo(198L, 1, "谷物");
        gwTreeVo.setEnumerationTreeVos(childList(164L));
        zzyChildList.add(gwTreeVo);

        // 薯类
        EnumerationTreeVo slTreeVo = new EnumerationTreeVo(198L, 2, "薯类");
        slTreeVo.setEnumerationTreeVos(childList(165L));
        zzyChildList.add(slTreeVo);
        // 豆类
        EnumerationTreeVo dlTreeVo = new EnumerationTreeVo(198L, 3, "豆类");
        dlTreeVo.setEnumerationTreeVos(childList(199L));
        zzyChildList.add(dlTreeVo);

        // 油料
        EnumerationTreeVo ylTreeVo = new EnumerationTreeVo(198L, 4, "油料");
        ylTreeVo.setEnumerationTreeVos(childList(167L));
        zzyChildList.add(ylTreeVo);

        // 棉花
        EnumerationTreeVo mhTreeVo = new EnumerationTreeVo(198L, 5, "棉花");
        mhTreeVo.setEnumerationTreeVos(childList(207L));
        zzyChildList.add(mhTreeVo);

        // 麻类
        EnumerationTreeVo myTreeVo = new EnumerationTreeVo(198L, 6, "麻类");
        myTreeVo.setEnumerationTreeVos(childList(200L));
        zzyChildList.add(myTreeVo);

        // 糖料
        EnumerationTreeVo tlTreeVo = new EnumerationTreeVo(198L, 7, "糖料");
        tlTreeVo.setEnumerationTreeVos(childList(201L));
        zzyChildList.add(tlTreeVo);

        // 烟草
        EnumerationTreeVo ycTreeVo = new EnumerationTreeVo(198L, 8, "烟草");
        ycTreeVo.setEnumerationTreeVos(childList(202L));
        zzyChildList.add(ycTreeVo);

        //蔬菜
        EnumerationTreeVo scTreeVo = new EnumerationTreeVo(198L, 9, "蔬菜");
        scTreeVo.setEnumerationTreeVos(childList(166L));
        zzyChildList.add(scTreeVo);

        //花卉
        EnumerationTreeVo hhTreeVo = new EnumerationTreeVo(198L, 10, "花卉");
        hhTreeVo.setEnumerationTreeVos(childList(203L));
        zzyChildList.add(hhTreeVo);

        //茶叶
        EnumerationTreeVo cyTreeVo = new EnumerationTreeVo(198L, 11, "茶叶");
        cyTreeVo.setEnumerationTreeVos(childList(204L));
        zzyChildList.add(cyTreeVo);

        //香料作物
        EnumerationTreeVo xlTreeVo = new EnumerationTreeVo(198L, 12, "香料作物");
        xlTreeVo.setEnumerationTreeVos(childList(205L));
        zzyChildList.add(xlTreeVo);

        //中药材
        EnumerationTreeVo zycTreeVo = new EnumerationTreeVo(198L, 13, "中药材");
        zycTreeVo.setEnumerationTreeVos(childList(169L));
        zzyChildList.add(zycTreeVo);

        //水果
        EnumerationTreeVo sgTreeVo = new EnumerationTreeVo(198L, 14, "水果");
        sgTreeVo.setEnumerationTreeVos(childList(170L));
        zzyChildList.add(sgTreeVo);

        //果用瓜类
        EnumerationTreeVo gyglTreeVo = new EnumerationTreeVo(198L, 15, "果用瓜类");
        gyglTreeVo.setEnumerationTreeVos(childList(206L));
        zzyChildList.add(gyglTreeVo);

        // 其他
        EnumerationTreeVo qtTreeVo = new EnumerationTreeVo(198L, 16, "其他");
        zzyChildList.add(qtTreeVo);

        return zzyChildList;
    }


    /**
     * 子项集合
     *
     * @return
     */
    private List<EnumerationTreeVo> childList(Long enumId) {
        List<EnumerationTreeVo> childList = new ArrayList<>();
        EnumerationItem enumItem = new EnumerationItem();
        enumItem.setEnumId(enumId);
        List<EnumerationItem> itemList = iEnumerationItemService.findList(enumItem);
        if (itemList == null) {
            return childList;
        }
        for (EnumerationItem item : itemList) {
            EnumerationTreeVo childVo = new EnumerationTreeVo(enumId, item.getValue(), item.getLabel());
            childList.add(childVo);
        }
        return childList;
    }



    @RequestMapping(value = "/findItemList", method = RequestMethod.POST)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询枚举值列表")
    public RespVO<RespDataVO<EnumerationItem>> findItemList(@RequestBody EnumerationItem enumerationItem) {

        return RespVOBuilder.success(iEnumerationItemService.findList(enumerationItem));
    }

    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询枚举列表")
    public RespVO<EnumerationAttribute> findList(@ModelAttribute Enumeration enumeration) {
        EnumerationAttribute attributes = new EnumerationAttribute();
        List<Enumeration> list = enumerationService.findList(enumeration);
        attributes.setEnumerations(list);
        if (list != null && list.size() > 0) {
            attributes.setLastUpdateDate(list.get(0).getLastUpdateDate());
            attributes.setFlag(false);
        }
        return RespVOBuilder.success(attributes);
    }


    @Operation(value = "findEnumerationList", desc = "根据id查询枚举label列表")
    @ApiOperation("根据id查询枚举label列表")
    @RequestMapping(value = "/findEnumerationList", method = RequestMethod.POST)
    public RespVO<Map<Long, String>> findEnumerationList(@RequestBody Map<Long, Integer> map) {
        Map<Long, String> param = new HashMap<>();
        if (map.size() > 0) {
            for (Map.Entry<Long, Integer> m : map.entrySet()) {
                Long key = m.getKey();
                Integer value = m.getValue();
                EnumerationItem item = new EnumerationItem();
                item.setEnumId(key);
                item.setValue(value);
                EnumerationItem finItem = iEnumerationItemService.finItem(item);
                param.put(finItem.getEnumId(), finItem.getLabel());
            }
        }
        return RespVOBuilder.success(param);
    }


    @RequestMapping(value = "/findModifyInfo/{code}", method = RequestMethod.GET)
    @Operation(value = "findModifyInfo", desc = "查询模板更新信息")
    @ApiOperation("查询模板更新信息")
    public RespVO<TemplateModifyInfo> findModifyInfo(@PathVariable String code,
                                                @RequestParam Integer type) {
        TemplateModifyInfo templateModifyInfo = new TemplateModifyInfo();
        templateModifyInfo.setType(type);
        templateModifyInfo.setTemplateCode(code);
        if (type == 1) {
            // 表单模板
            FormTemplate formTemplate = new FormTemplate();
            formTemplate.setTemplateCode(code);
            FormTemplate template = iFormTemplateService.find(formTemplate);
            if (template != null) {
                templateModifyInfo.setLastUpdateDate(template.getLastUpdateDate());
            }
        } else if (type == 2) {
            // 数据模板
            DataTemplate dataTemplate = new DataTemplate();
            dataTemplate.setTemplateCode(code);
            DataTemplate template = iDataTemplateService.find(dataTemplate);
            if (template != null) {
                templateModifyInfo.setLastUpdateDate(template.getLastUpdateDate());
            }
        }
        return RespVOBuilder.success(templateModifyInfo);
    }


    @RequestMapping(value = "/findModifyList", method = RequestMethod.GET)
    @Operation(value = "findModifyList", desc = "查询枚举更新时间")
    @ApiOperation("查询枚举更新时间")
    public RespVO<EnumerationModifyInfo> findModifyList(@ModelAttribute Enumeration enumeration) {
        EnumerationModifyInfo enumerationModifyInfo = new EnumerationModifyInfo();
        List<Enumeration> enumerations = enumerationService.findList(enumeration);
        if (enumerations != null && enumerations.size() > 0) {
            enumerationModifyInfo.setLastUpdateDate(enumerations.get(0).getLastUpdateDate());
        }
        List<TemplateModifyInfo> modifyList = new ArrayList<>();
        List<FormTemplate> all = iFormTemplateService.findAll();
        if (all != null) {
            for (FormTemplate formTemplate : all) {
                TemplateModifyInfo modifyInfo = new TemplateModifyInfo();
                modifyInfo.setTemplateCode(formTemplate.getTemplateCode());
                modifyInfo.setLastUpdateDate(formTemplate.getLastUpdateDate());
                modifyInfo.setType(1);
                modifyList.add(modifyInfo);
            }
        }
        List<DataTemplate> all1 = iDataTemplateService.findAll();
        if (all1 != null) {
            for (DataTemplate dataTemplate : all1) {
                TemplateModifyInfo modifyInfo = new TemplateModifyInfo();
                modifyInfo.setTemplateCode(dataTemplate.getTemplateCode());
                modifyInfo.setLastUpdateDate(dataTemplate.getLastUpdateDate());
                modifyInfo.setType(2);
                modifyList.add(modifyInfo);
            }
        }
        enumerationModifyInfo.setTemplateModifyInfos(modifyList);
        return RespVOBuilder.success(enumerationModifyInfo);
    }


    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询枚举详情")
    public RespVO<Enumeration> find(@PathVariable Long id) {
        Enumeration enumeration = enumerationInit.getEnumerationIdMap().get(id);
        return RespVOBuilder.success(enumeration);
    }


    @RequestMapping(value = "/findItem/{enumId}/{value}", method = RequestMethod.GET)
    @Operation(value = "findItem", desc = "查询")
    @ApiOperation("查询枚举值")
    public RespVO<EnumerationItem> findItem(@PathVariable(value = "enumId") Long enumId, @PathVariable(value = "value") Integer value) {
        EnumerationItem item = new EnumerationItem();
        item.setEnumId(enumId);
        item.setValue(value);
        EnumerationItem enumerationItem = iEnumerationItemService.finItem(item);
        return RespVOBuilder.success(enumerationItem);
    }

    @Operation(value = "findItem", desc = "查询")
    @ApiOperation("查询枚举值")
    @RequestMapping(value = "/findItemLable/{enumId}/{label}", method = RequestMethod.GET)
    public RespVO<EnumerationItem> findItemLable(@PathVariable(value = "enumId") Long enumId,
                                            @PathVariable(value = "label") String label) {
        EnumerationItem item = new EnumerationItem();
        item.setEnumId(enumId);
        item.setLabel(label);
        EnumerationItem enumerationItem = iEnumerationItemService.finItem(item);
        return RespVOBuilder.success(enumerationItem);
    }


    @RequestMapping(value = "/findByCode/{code}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询枚举详情")
    public RespVO<Enumeration> findByCode(@PathVariable String code) {
        Enumeration enumeration = enumerationInit.getEnumerationCodeMap().get(code);
        return RespVOBuilder.success(enumeration);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.PUT)
    @Operation(value = "insert", desc = "新增")
    @ApiOperation("新增")
    public RespVO insert(@RequestBody Enumeration enumeration) {

        return enumerationService.insert(enumeration);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("更新")
    public RespVO update(@RequestBody Enumeration template) {

        return enumerationService.update(template);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @Operation(value = "delete", desc = "删除")
    @ApiOperation("删除")
    public RespVO delete(@PathVariable Long id) {

        return enumerationService.delete(id);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("刷新")
    public RespVO refresh() {
        enumerationInit.initEnumMap();
        return RespVOBuilder.success();
    }


}
