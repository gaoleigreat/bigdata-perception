package com.lego.framework.base.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yanglf
 * @description
 * @since 2019/6/26
 **/
public class EurekaUtil {

    /**
     * 获取服务的所有地址（注册在 eureka server 上的服务）
     *
     * @param eurekaIp
     * @param eurekaPort
     * @param serviceName
     * @return
     */
    public static List<String> getAllServiceAddr(String eurekaIp, String eurekaPort, String serviceName) {
        List<String> result = new ArrayList<>();
        String url = "http://" + eurekaIp + ":" + eurekaPort + "/eureka/apps/" + serviceName;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                //请求接口。如果需要传参拼接到接口后面。
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/xml")
                .get()
                .build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseContent = response.body().string();
                Matcher matcher = Pattern.compile("<homePageUrl>(.+?)</homePageUrl>").matcher(responseContent);
                while (matcher.find()) {
                    String homepage = matcher.group(1).trim();
                    result.add(homepage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 随机获取一个微服务的地址
     *
     * @param eurekaIp
     * @param eurekaPort
     * @param servicename
     * @return
     */
    public static String getOneServiceAddr(String eurekaIp, String eurekaPort, String serviceName) {
        List<String> result = new ArrayList<>();
        String url = "http://" + eurekaIp + ":" + eurekaPort + "/eureka/apps/" + serviceName;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                //请求接口。如果需要传参拼接到接口后面。
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/xml")
                .get()
                .build();//创建Request 对象
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseContent = response.body().string();
                Matcher matcher = Pattern.compile("<homePageUrl>(.+?)</homePageUrl>").matcher(responseContent);
                while (matcher.find()) {
                    String homepage = matcher.group(1).trim();
                    if (homepage.endsWith("/")) {
                        homepage = homepage.substring(0, homepage.length() - 1);
                    }
                    result.add(homepage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result.isEmpty()) {
            return "";
        } else {
            Collections.shuffle(result);
            return result.get(0);
        }
    }


}
