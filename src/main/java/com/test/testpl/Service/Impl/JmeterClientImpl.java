package com.test.testpl.Service.Impl;

import com.test.testpl.Service.JmeterClient;
import com.test.testpl.common.request.HttpSamplerSettings;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.engine.JMeterEngineException;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.samplers.SampleSaveConfiguration;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.save.converters.SampleSaveConfigurationConverter;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.backend.AbstractBackendListenerClient;
import org.apache.jorphan.collections.HashTree;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jorphan.collections.ListedHashTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class JmeterClientImpl implements JmeterClient {

    private final static String JMETER_HOME = getJmeterHome();
//    @Autowired
//    private ResultListener resultListener;

    @Override
    public void runTestPlan(HttpSamplerSettings settings) throws IOException {
        //jmeter的一些初始化的设置
        init();
        //目前传进来的只有一个http请求，需要创建出一个测试计划，配置好线程组、采样器、结果树等；
        HashTree hashTree = CreateTestPlanTree(settings);
        HashTree2Jmx(hashTree, "F:\\projects\\a2023\\javaProject\\TestPL");
        //测试引擎
        JMeterEngine engine = new StandardJMeterEngine();
        //要监听结果的话，需要注册 SampleListener
        try {
            // 代码块
            engine.configure(hashTree);
        } catch (Exception e) {
            e.printStackTrace(); // 打印堆栈跟踪信息
        }

        try {
            engine.runTest();
        } catch (JMeterEngineException e) {
            engine.stopTest(true);
        }
    }

    public  static void init() throws IOException {
        String JMETER_PROPERTIES = JMETER_HOME + "/jmeter.properties";
        JMeterUtils.loadJMeterProperties(JMETER_PROPERTIES);
        JMeterUtils.setJMeterHome(JMETER_HOME);
        JMeterUtils.setLocale(LocaleContextHolder.getLocale());
    }

    public  void defaultTestPlanSet(TestPlan testPlan){
        testPlan.setName("测试计划");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables(new Arguments());
    };


    public void defaultGroupSet(ThreadGroup  threadGroup, LoopController loopController){
        threadGroup.setName("线程组1");
        threadGroup.setNumThreads(1);
        threadGroup.setRampUp(1);
        threadGroup.setScheduler(true);
        threadGroup.setDuration(60);
        threadGroup.setDelay(0);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroup.class.getName());
        loopController.setLoops(1);
        loopController.setContinueForever(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopController.class.getName());
        loopController.initialize();
        threadGroup.setSamplerController(loopController);
    }
    public void SamplerSet(HTTPSamplerProxy httpSamplerProxy, HttpSamplerSettings settings) {
        //看看是不是有请求头
        if(settings.getHeaders()!=null) {
            httpSamplerProxy.setHeaderManager(AddHeadManager(settings.getHeaders()));
            System.out.println("http head manager:" + httpSamplerProxy.getHeaderManager().getHeaders().toString());
        }
        //基础设置,域名啊，端口啊，方法啊，路径啊
        httpSamplerProxy.setName("用例名称");
        httpSamplerProxy.setDomain(settings.getDomain());
        httpSamplerProxy.setPath(settings.getPath());
        httpSamplerProxy.setMethod(settings.getMethod());
        httpSamplerProxy.setUseKeepAlive(true);
        httpSamplerProxy.setConnectTimeout(settings.getTimeout());

        //请求体设置
        if("POST".equals(settings.getMethod())) {
            httpSamplerProxy.setPostBodyRaw(true);
            httpSamplerProxy.addArgument("", settings.getRequestBody());
            System.out.println("getArguments" + httpSamplerProxy.getArguments().toString());
        }

        httpSamplerProxy.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSamplerProxy.setProperty(TestElement.GUI_CLASS, HTTPSamplerProxy.class.getName());
        System.out.println("http 请求的参数："+ httpSamplerProxy.getArguments().toString());
        System.out.println("http其他参数："+ httpSamplerProxy.getDomain()+"," + httpSamplerProxy.getMethod() +"," +httpSamplerProxy.getName()+"," +httpSamplerProxy.getPath());
    }

    private HeaderManager AddHeadManager(Map<String,String> headers) {
        // Create header manager
        HeaderManager headerManager = new HeaderManager();
        headers.forEach((key,value)->{
            Header h = new Header(key, value);
            headerManager.add(h);
        } );
        System.out.println("headers:" + headerManager.getHeaders().toString());
        return headerManager;
    }
public  HashTree CreateTestPlanTree(HttpSamplerSettings settings){
    //ListedHashTree是HashTree的继承类，可以保证HashTree的顺序性
    HashTree tree = new HashTree();
    //1、TestPlan对象，测试计划
    TestPlan plan = new TestPlan();
    defaultTestPlanSet(plan);

    //2、ThreadGroup对象，线程组
    ThreadGroup group = new ThreadGroup();
    LoopController loopController = new LoopController();
    defaultGroupSet(group,loopController);
    //创建线程组数结构的对象groupTree
    HashTree groupTree = new HashTree();

    //3、表示取样器中的HTTP请求
    HTTPSamplerProxy sampler = new HTTPSamplerProxy();
    SamplerSet(sampler,settings);

    //4、创建一个结果收集器
    ResultCollector listener = new ResultListener();

    //5 调用put方法相当于在plan（测试计划）菜单对象下添加group（线程组）子菜单，这样就形成了一种树型结构
    HashTree resultTree = new HashTree();
    HashTree samplerTree = new HashTree();
    samplerTree.add(sampler,new HashTree());
//    resultTree.add(resultCollector, new HashTree());
    resultTree.add(listener,new HashTree());

    //6 groupTree树结构添加子树samplerTree
    groupTree.add(group,samplerTree);
    groupTree.add(group,resultTree);
    //tree树结构为测试计划对象，添加子树groupTree，这样就形成了上图的层级形式
    tree.add(plan, groupTree);
    return tree;
}

    private static String getJmeterHome() {
        String home = JmeterClientImpl.class.getResource("/").getPath() + "jmeter";
        return home;
    }

    public void Jmx2HashTree(String filePath) throws IOException {
        init();
        SaveServiceInit();
        try {
        HashTree hashTree = SaveService.loadTree(new File(filePath));
        System.out.println(hashTree.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void HashTree2Jmx(HashTree hashTree,String savePath) throws IOException {
        init();
        SaveServiceInit();
        try {
           SaveService.saveTree(hashTree,new FileOutputStream("myhashTree"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private  void SaveServiceInit(){
        try {
            String JMETER_SAVE_PROPERTIES = JMETER_HOME + "/saveservice.properties";
            JMeterUtils.setProperty("saveservice_properties",JMETER_SAVE_PROPERTIES);
            SaveService.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
