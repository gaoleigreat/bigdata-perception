package com.lego.framework.data.model.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.BeanUtils;
/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/21 16:00
 * @desc :
 */
@Data
@TableName(value = "datasharedtable")
public class RemoteSharedData extends BaseData {
    /**
     * remoteSharedData to localSharedData
     *
     * @return
     */
    public LocalSharedData remote2LocalSharedData() {
        LocalSharedData localSharedData = new LocalSharedData();
        BeanUtils.copyProperties(this, localSharedData);
        return localSharedData;
    }

    public RemoteSharedData() {

    }



}
