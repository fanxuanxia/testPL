package com.test.testpl.Service.Impl;

import com.alibaba.fastjson2.JSON;
import com.test.testpl.Dao.entity.ExecutionHistoryRecordEntity;
import com.test.testpl.Service.CaseService;
import com.test.testpl.common.utils.TimestampUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;


public class ResultListener extends ResultCollector {

    /*@Autowired
    private CaseServiceImpl caseServiceImpl;*/

    //原来的方法是把执行结果保存到本地，需要重写方法，把响应结果返回给前端
    @Override
    public void sampleOccurred(SampleEvent event) {
        // 在每个样本发生时获取执行结果并输出
        SampleResult result = event.getResult();
        System.out.println("执行结果：");
        System.out.println("请求 URL：" + result.getUrlAsString());
        System.out.println("响应码：" + result.getResponseCode());
        System.out.println("响应消息：" + result.getResponseMessage());
        System.out.println("响应时间：" + result.getTime());
        System.out.println("响应时间：" + result.getResponseDataAsString());
        // 其他执行结果属性的获取和输出
        ExecutionHistoryRecordEntity execuResult= new ExecutionHistoryRecordEntity();
        if(result.isSuccessful()){
            execuResult.setExecuteResult(1);
        }else{
            execuResult.setExecuteResult(0);
        }
        execuResult.setExecuteTime(TimestampUtils.convertTimestampToDate(result.getEndTime()));
//        execuResult.setRequestBody();
        execuResult.setRequestHeaders(result.getRequestHeaders());
        execuResult.setResponseCode(result.getResponseCode());
        execuResult.setResponseHeaders(result.getResponseHeaders());
        execuResult.setResponseBody(result.getResponseDataAsString());
        //发送消息
        sendFinishMessage(execuResult);
    }


    private void sendFinishMessage(ExecutionHistoryRecordEntity executionHistoryRecord){
        // 配置 Kafka 生产者属性
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // 创建 Kafka 生产者工厂
        DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);

        // 创建 KafkaTemplate 实例
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        ListenableFuture listenableFuture = kafkaTemplate.send("CaseFinished", JSON.toJSONString(executionHistoryRecord));
        Boolean res = false;
        //回调方法
        listenableFuture.addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("消息发送失败");
            }

            @Override
            public void onSuccess(Object result) {
                System.out.println("消息发送成功");
            }
        });
    }
}
