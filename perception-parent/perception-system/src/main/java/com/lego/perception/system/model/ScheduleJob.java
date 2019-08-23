package com.lego.perception.system.model;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(ScheduleJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String url = (String) jobDetail.getJobDataMap().get("url");
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.createDefault();

            URIBuilder uriBuilder = new URIBuilder(url);
//            uriBuilder.addParameter("idCard", "");
//            uriBuilder.addParameter("name", "");

            HttpGet get = new HttpGet(uriBuilder.build());
            //get.addHeader("Authorization","APPCODE " + appCode);
            response = httpClient.execute(get);

            Integer statusCode = response.getStatusLine().getStatusCode();
            if(null != statusCode && 200 == statusCode){
                log.info(jobDetail.getJobDataMap().getString(
                        "jobName")+"----"+jobDetail.getJobDataMap().getString("groupName")
                        +" url:  "+url
                        +" return:"+ EntityUtils.toString(response.getEntity()));
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
