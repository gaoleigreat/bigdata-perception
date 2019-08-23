package com.lego.framework.base.sdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2018/12/22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RespDataVO<T> {
    private List<T> list;
}
