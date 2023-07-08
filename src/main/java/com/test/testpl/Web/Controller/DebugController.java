package com.test.testpl.Web.Controller;

import com.test.testpl.Dao.entity.ApiDefinitionEntity;
import com.test.testpl.Service.Impl.DebugServiceImpl;
import com.test.testpl.Service.Impl.JmeterClientImpl;
import com.test.testpl.common.reponse.BaseResponse;
import com.test.testpl.common.request.HttpSamplerSettings;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class DebugController {


    @Autowired
    private DebugServiceImpl debugService;

//    @PostMapping("/testDebug")
//    public void testDebug(@RequestBody HttpSamplerSettings settings){
//        System.out.println("testDebug");
//        try {
//            jmeterClient.runTestPlan(settings);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    @PostMapping("/testDebug")
    public void testDebug(@RequestBody HttpSamplerSettings settings) {
        System.out.println("testDebug");
        System.out.println(ReflectionToStringBuilder.toString(settings));
        try {
            debugService.debug(settings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/saveApiInfo")
    public BaseResponse saveApiInfo(@RequestBody ApiDefinitionEntity apiInfo){
        System.out.println("saveApiInfo");
        System.out.println("ApiDefinition Request: " + ReflectionToStringBuilder.toString(apiInfo));
        return BaseResponse.success( debugService.saveApi(apiInfo));

    }

//
//    @GetMapping("/toHashTree")
//      public  void toHashTree () throws IOException {
//        jmeterClient.Jmx2HashTree("F:\\projects\\2023\\javaProject\\TestPL\\src\\main\\resources\\jmeter\\btstuTestPlan-000006.jmx");
//    }
}
