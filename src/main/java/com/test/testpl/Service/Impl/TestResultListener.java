package com.test.testpl.Service.Impl;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.visualizers.backend.AbstractBackendListenerClient;
import org.apache.jmeter.visualizers.backend.BackendListenerContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//继承AbstractBackendListenerClient来将异步获取到的测试结果集SampleResult进行相应处理
public class TestResultListener extends AbstractBackendListenerClient{

    //重写handleSampleResults方法
    @Override
    public void handleSampleResults(List<SampleResult> list, BackendListenerContext backendListenerContext) {
        System.out.println("[INFO],打印测试结果"  );
        System.out.println("SampleResult"+ ReflectionToStringBuilder.toString(list));
        System.out.println("backendListenerContext"+ ReflectionToStringBuilder.toString(backendListenerContext));

    }
}
