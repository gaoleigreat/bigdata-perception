package com.lego.framework.data.model.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/21 15:59
 * @desc :
 */
@Data
@TableName(value = "tpl_share_data")
public class LocalSharedData extends BaseData {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("批次号")
    private String batchNum;

    /**
     * localSharedData to   remoteSharedData
     *
     * @return
     */
    public RemoteSharedData local2RemoteData() {
        RemoteSharedData remoteSharedData = new RemoteSharedData();
        BeanUtils.copyProperties(this, remoteSharedData);
        return remoteSharedData;
    }

    public LocalSharedData() {

    }
}
