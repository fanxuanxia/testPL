package com.test.testpl.Service.Impl;

import com.test.testpl.Service.MyHttpClient;
import com.test.testpl.common.utils.GetApiConfigs;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

@Service
public class HttpClientImpl implements MyHttpClient {

    private CloseableHttpClient client = HttpClients.createDefault();

    @Autowired
    private GetApiConfigs getApiConfigs;

    @Override
    public void doGet() throws IOException {
        HttpGet httpGet = new HttpGet();
        build(httpGet);

        //发送get请求
        HttpResponse response = client.execute(httpGet);

        // 获取响应码
        int statusCode = response.getStatusLine().getStatusCode();
        // 获取全部的请求头
        Header[] allHeaders = response.getAllHeaders();
        System.out.println("响应状态码：" + statusCode);

        for (int i = 0; i < allHeaders.length; i++) {
            System.out.println("全部的响应头：" + allHeaders[i]);
        }
        Header[] allHeaders1 = httpGet.getAllHeaders();
        for (int i = 0; i < allHeaders1.length; i++) {
            System.out.println("全部的请求头：" + allHeaders1[i]);
        }
        System.out.println("请求方法：" + httpGet.getMethod());
        System.out.println("RequestLine：" + httpGet.getRequestLine());
        HttpEntity entity = response.getEntity();
    }


    public void doPost() throws IOException {
        HttpPost post = new HttpPost();
        build(post);

        //发送get请求
        HttpResponse response = client.execute(post);

        // 获取响应码
        int statusCode = response.getStatusLine().getStatusCode();
        // 获取全部的请求头
        Header[] allHeaders = response.getAllHeaders();
        System.out.println("响应状态码：" + statusCode);

        for (int i = 0; i < allHeaders.length; i++) {
            System.out.println("全部的响应头：" + allHeaders[i]);
        }
        Header[] allHeaders1 = post.getAllHeaders();
        for (int i = 0; i < allHeaders1.length; i++) {
            System.out.println("全部的请求头：" + allHeaders1[i]);
        }
        System.out.println("请求方法：" + post.getMethod());
        System.out.println("RequestLine：" + post.getRequestLine());
        HttpEntity entity = response.getEntity();
    }

    public void build(HttpRequestBase httpRequestBase) {
        Map<String, Map<String, String>> config = getApiConfigs.AnalyzeConfig();
        if (!config.isEmpty()) {
            //设置请求URL
            httpRequestBase.setURI(URI.create(config.get("baseURL").get("baseURL") +"?charset=utf-8&encode=json"));

            //从设置请求头
            Map<String, String> headers = config.get("headers");
            headers.forEach((k, v) -> {
                httpRequestBase.addHeader(k, v);
            });
        }
    }

}
