package com.test.testpl.Service;

import com.test.testpl.common.request.HttpSamplerSettings;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jorphan.collections.HashTree;

import java.io.IOException;

public interface JmeterClient {
    void defaultTestPlanSet(TestPlan testPlan);
    void defaultGroupSet(ThreadGroup  threadGroup, LoopController loopController);
    void SamplerSet(HTTPSamplerProxy httpSamplerProxy, HttpSamplerSettings settings);
    HashTree CreateTestPlanTree(HttpSamplerSettings settings);
    void  runTestPlan(HttpSamplerSettings settings) throws IOException;
}
