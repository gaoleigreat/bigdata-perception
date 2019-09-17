package com.lego.perception.data.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Arrays;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/16 11:16
 * @desc :
 */
@Slf4j
public class QueryUtils {

    /**
     * @param criteria
     * @param symbol
     * @param absoluteField
     * @param value
     */
    public static void addAdvancedCondition(Criteria criteria, String symbol, String absoluteField, String value) {
        if (">=".equals(symbol)) {
            criteria.and(absoluteField).gte(Integer.valueOf(value));
        } else if (">".equals(symbol)) {
            criteria.and(absoluteField).gt(Integer.valueOf(value));
        } else if ("<=".equals(symbol)) {
            criteria.and(absoluteField).lte(Integer.valueOf(value));
        } else if ("<".equals(symbol)) {
            criteria.and(absoluteField).lt(Integer.valueOf(value));
        } else if ("=".equals(symbol)) {
            criteria.and(absoluteField).is(Integer.valueOf(value));
        } else if ("like".equals(symbol)) {
            criteria.and(absoluteField).regex("^.*"+value+".*$");
        } else if ("notExists".equals(symbol)) {
            criteria.and(absoluteField).exists(false);
        } else if ("exists".equals(symbol)) {
            criteria.and(absoluteField).exists(true);
        } else if ("in".equals(symbol)) {
            try {
                if (value.lastIndexOf(",") > 0) {

                    String[] val = value.split(",");
                    Integer[] vals = new Integer[val.length];
                    for (int i = 0; i < val.length; i++) {
                        vals[i] = Integer.valueOf(val[i]);
                    }
                    criteria.and(absoluteField).in(Arrays.asList(vals));

                } else {
                    criteria.and(absoluteField).in(Arrays.asList(new Integer[]{Integer.valueOf(value)}));
                }
            } catch (Exception e) {
                log.error("", e);
            }
        } else if ("notin".equals(symbol)) {
            try {
                if (value.lastIndexOf(",") > 0) {
                    String[] val = value.split(",");
                    Integer[] vals = new Integer[val.length];
                    for (int i = 0; i < val.length; i++) {
                        vals[i] = Integer.valueOf(val[i]);
                    }
                    criteria.and(absoluteField).nin(Arrays.asList(vals));
                } else {
                    criteria.and(absoluteField).nin(Arrays.asList(new Integer[]{Integer.valueOf(value)}));
                }
            } catch (Exception e) {
                log.error("", e);
            }
        } else if ("true".equals(symbol)) {
            criteria.and(absoluteField).is(true);
        } else if ("false".equals(symbol)) {
            criteria.and(absoluteField).is(false);
        } else {
            throw new RuntimeException("查询符号错误，不能识别的符号");
        }
    }

}
