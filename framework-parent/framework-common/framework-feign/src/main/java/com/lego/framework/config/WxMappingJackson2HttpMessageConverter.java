package com.lego.framework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/10/19 20:24
 * @desc :
 */
public class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public WxMappingJackson2HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_HTML);
        /*ObjectMapper objectMapper1 = new ObjectMapper();
        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper1.setDateFormat(smt);
        setObjectMapper(objectMapper1);*/
        setSupportedMediaTypes(mediaTypes);

    }
}
