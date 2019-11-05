package com.lego.perception.statistics.controller;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.data.feign.PerceptionStructuredDataClient;
import com.lego.framework.data.feign.PerceptionUnstructuredDataClient;
import com.lego.framework.data.model.entity.PerceptionStructuredData;
import com.lego.framework.data.model.entity.PerceptionUnstructuredData;
import com.lego.framework.equipment.feign.EquipmentCostClient;
import com.lego.framework.equipment.feign.EquipmentMaintenanceClient;
import com.lego.framework.equipment.feign.EquipmentServiceClient;
import com.lego.framework.equipment.model.entity.EquipmentType;
import com.lego.framework.file.feign.PerceptionFileClient;
import com.lego.framework.file.model.PerceptionFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.schema.Entry;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther xiaodao
 * @date 2019/10/24 10:09
 */

@Api(value = "PerceptionDataStatsticController", description = "感知数据统计")
@RestController
@RequestMapping("/perceptionDataStatsticController")
@Resource(value = "perceptionDataStatsticController", desc = "感知数据统计")
public class PerceptionDataStatsticController {
    private List<String> lables = Arrays.asList("水文环境", "地形地貌", "市政管线", "勘探设计", "工程施工", "装备运行", "运营维护", "其他");

    @Autowired
    private PerceptionStructuredDataClient perceptionStructuredDataClient;

    @Autowired
    private PerceptionUnstructuredDataClient perceptionUnstructuredDataClient;

    @Autowired
    private PerceptionFileClient perceptionFileClient;

    @ApiOperation("感知文件批次数量")
    @GetMapping("/perceivedFileBatchCount")
    public RespVO perceivedFileBatchCount() {
        List<DataTransfer> resultList = new ArrayList<>();
        PerceptionStructuredData psd = new PerceptionStructuredData();
        PerceptionUnstructuredData pusd = new PerceptionUnstructuredData();
        psd.setDeleteFlag(0);
        pusd.setDeleteFlag(0);
        RespVO<RespDataVO<PerceptionStructuredData>> respVOPsd = perceptionStructuredDataClient.query(psd);
        RespVO<RespDataVO<PerceptionUnstructuredData>> respVOPusd = perceptionUnstructuredDataClient.query(pusd);
        DataTransfer dataTransferS = new DataTransfer();
        dataTransferS.setLable("结构化数据批次");
        if (respVOPsd.getRetCode() == 1) {
            dataTransferS.setValue(CollectionUtils.isEmpty(respVOPsd.getInfo().getList()) ? 0 : respVOPsd.getInfo().getList());
        } else {
            dataTransferS.setValue(0);
        }
        DataTransfer dataTransferUs = new DataTransfer();
        dataTransferUs.setLable("非结构化数据批次");
        if (respVOPusd.getRetCode() == 1) {
            dataTransferUs.setValue(CollectionUtils.isEmpty(respVOPusd.getInfo().getList()) ? 0 : respVOPusd.getInfo().getList());
        } else {
            dataTransferUs.setValue(0);
        }
        resultList.add(dataTransferS);
        resultList.add(dataTransferUs);

        return RespVOBuilder.success(resultList);
    }


    @ApiOperation("感知文件数量")
    @GetMapping("/perceivedFileCount")
    public RespVO perceivedFileCount() {
        List<DataTransfer> resultList = new ArrayList<>();
        PerceptionFile perceptionFile = new PerceptionFile();
        perceptionFile.setDeleteFlag(0);
        RespVO<RespDataVO<PerceptionFile>> dataVORespVO = perceptionFileClient.query(perceptionFile);
        if (dataVORespVO.getRetCode() == 1) {
            List<PerceptionFile> list = dataVORespVO.getInfo().getList();
            Map<Integer, Long> collect = list.stream().collect(Collectors.groupingBy(PerceptionFile::getIsStructured, Collectors.counting()));

            DataTransfer dataTransferS = new DataTransfer();
            DataTransfer dataTransferUs = new DataTransfer();
            dataTransferS.setLable("结构化数据数量");
            dataTransferS.setValue(collect.get(0) == null ? 0 : collect.get(0));
            dataTransferUs.setLable("非结构化数据数量");
            dataTransferUs.setValue(collect.get(1) == null ? 0 : collect.get(1));
            resultList.add(dataTransferS);
            resultList.add(dataTransferUs);
        } else {

            DataTransfer dataTransferS = new DataTransfer();
            DataTransfer dataTransferUs = new DataTransfer();
            dataTransferS.setLable("结构化数据数量");
            dataTransferS.setValue(0);
            dataTransferUs.setLable("非结构化数据数量");
            dataTransferUs.setValue(0);
            resultList.add(dataTransferS);
            resultList.add(dataTransferUs);
        }
        return RespVOBuilder.success(resultList);

    }

    @ApiOperation("文件类型占比")
    @GetMapping("/fileTypeRatio")
    public RespVO fileTypeRatio() {
        List<DataTransfer> resultList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");
        PerceptionFile perceptionFile = new PerceptionFile();
        perceptionFile.setDeleteFlag(0);
        RespVO<RespDataVO<PerceptionFile>> dataVORespVO = perceptionFileClient.query(perceptionFile);
        if (dataVORespVO.getRetCode() == 1) {
            List<PerceptionFile> list = dataVORespVO.getInfo().getList();
            Map<String, Long> collect = list.stream().collect(Collectors.groupingBy(PerceptionFile::getBusinessModule, Collectors.counting()));
            lables.forEach(s -> {
                DataTransfer dataTransfer = new DataTransfer();
                dataTransfer.setLable(s);
                dataTransfer.setValue(df.format(collect.get(s) == null || list.size() == 0 ? 0 : collect.get(s) / list.size()));
                resultList.add(dataTransfer);
            });

        } else {
            lables.forEach(s -> {
                DataTransfer dataTransfer = new DataTransfer();
                dataTransfer.setLable(s);
                dataTransfer.setValue(0);
                resultList.add(dataTransfer);
            });
        }
        return RespVOBuilder.success(resultList);

    }

    @ApiOperation("共享文件类型数量")
    @GetMapping("/fileTypeCount")
    public RespVO fileTypeCount() {
        List<DataTransfer> resultList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");
        PerceptionFile perceptionFile = new PerceptionFile();
        perceptionFile.setDeleteFlag(0);
        RespVO<RespDataVO<PerceptionFile>> dataVORespVO = perceptionFileClient.query(perceptionFile);
        if (dataVORespVO.getRetCode() == 1) {
            List<PerceptionFile> list = dataVORespVO.getInfo().getList();
            Map<String, Long> collect = list.stream().collect(Collectors.groupingBy(PerceptionFile::getBusinessModule, Collectors.counting()));
            lables.forEach(s -> {
                DataTransfer dataTransfer = new DataTransfer();
                dataTransfer.setLable(s);
                dataTransfer.setValue(collect.get(s) == null ? 0 : collect.get(s));
                resultList.add(dataTransfer);
            });

        } else {
            lables.forEach(s -> {
                DataTransfer dataTransfer = new DataTransfer();
                dataTransfer.setLable(s);
                dataTransfer.setValue(0);
                resultList.add(dataTransfer);
            });
        }
        return RespVOBuilder.success(resultList);

    }


}


class DataTransfer {
    public String getLable() {
        return label;
    }

    public void setLable(String lable) {
        this.label = lable;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    private String label;

    private Object value;
}