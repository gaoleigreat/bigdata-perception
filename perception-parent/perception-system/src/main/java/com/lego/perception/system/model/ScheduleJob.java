package com.lego.perception.system.model;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class ScheduleJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String jobName = jobDataMap.getString("jobName");
        String groupName = jobDataMap.getString("groupName");
        log.info("execute time:{},jobName:{},groupName:{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                jobName, groupName);
        String url = jobDataMap.getString("url");
        CloseableHttpResponse response;
        CloseableHttpClient httpClient;
        try {
            httpClient = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(url);
//            uriBuilder.addParameter("idCard", "");
//            uriBuilder.addParameter("name", "");
            HttpGet get = new HttpGet(uriBuilder.build());
            //get.addHeader("Authorization","APPCODE " + appCode);
            response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                log.info(jobName + "----" + groupName
                        + " url:  " + url
                        + " return:" + EntityUtils.toString(response.getEntity()));
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
