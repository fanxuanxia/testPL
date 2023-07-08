package com.test.testpl.Web.Controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.test.testpl.Dao.dto.ExecuteResultDto;
import com.test.testpl.Dao.dto.TestCaseDto;
import com.test.testpl.Dao.entity.ApiDefinitionEntity;
import com.test.testpl.Dao.entity.TestCaseEntity;
import com.test.testpl.Service.Impl.CaseServiceImpl;
import com.test.testpl.Service.Impl.HttpClientImpl;
import com.test.testpl.common.enums.TestCaseStatus;
import com.test.testpl.common.reponse.BaseResponse;
import com.test.testpl.common.reponse.SingleResult;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class CaseController {
    @Autowired
    private CaseServiceImpl caseServiceImpl;
    @Autowired
    private HttpClientImpl httpClient;

    @GetMapping("/getCaseById")
    @ResponseBody
    public TestCaseEntity getCaseById() {
      return caseServiceImpl.getCaseById(1);
    }

    @GetMapping("/caseList/getALLCase")
    @ResponseBody
    public BaseResponse getALLCase() {
        return BaseResponse.success(caseServiceImpl.getAllValidCase());
//        return BaseResponse.success(caseServiceImpl.getAllCaseAndExecuteHistory());
    }


    @PostMapping("/caseList/getCaseExecuteHistory")
    @ResponseBody
    public BaseResponse getCaseExecuteHistory(@RequestBody TestCaseEntity request) {
        System.out.println("getHistory");
        System.out.println("请求参数：" +JSON.toJSONString(request));
        Integer caseId = request.getId();
        return BaseResponse.success(caseServiceImpl.getExecuteHistoryByCaseId(caseId));
    }

    @PostMapping("/addCase")
    public BaseResponse addCase(@RequestBody TestCaseDto testCaseDto){
        System.out.println("[INFO]: 开始添加用例" );
        System.out.println("收到的参数：" + ReflectionToStringBuilder.toString(testCaseDto));
        Object res =caseServiceImpl.addCase(testCaseDto);
        if(res!=null){
            return BaseResponse.success(res);
        }else return BaseResponse.error("请先调试接口");
    }

    @PostMapping("/caseList/delectCaseById")
    @ResponseBody
    public BaseResponse delectCaseById(@RequestBody TestCaseEntity request){
        System.out.println("[INFO]: 开始删除用例" );
        //不能用物理删除，改一下状态就可以了
        Integer res = caseServiceImpl.updateCaseStatusById(request.getId(),TestCaseStatus.DELETED);
        if(res !=0){
            return BaseResponse.success(SingleResult.create("result",res));
        }else{
            System.out.println("[INFO] 用例状态修改失败了");
            return BaseResponse.error("删除失败！");
        }
    }


    @PostMapping("/caseList/executeCase")
    @ResponseBody
    public BaseResponse executeCase(@RequestBody TestCaseEntity request){
        System.out.println("[INFO]: 开始执行用例" );
        ExecuteResultDto res= caseServiceImpl.executeCase(request.getId());
         return BaseResponse.success(res);
    }

    @GetMapping("/ll")
    public void getCaseServiceImpl() {
        try {
            httpClient.doGet();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/pp")
    public void sendPostRequest() {
        try {
            httpClient.doPost();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @PostMapping("addCaseByMsg")
//    public  void addCaseByMsg(@RequestBody ApiDefinition apiDefinition){
//        System.out.println("执行到了addCaseByMsg");
//        String s = JSON.toJSONString(apiDefinition);
//        System.out.println("s:" + s);
//        caseServiceImpl.addCaseByMsg(s);
//        System.out.println("finish");
//    }

//    @KafkaListener(topics = "test")
//    public void  listenMsg(String msgContent){
//        System.out.println("监听到消息：" + msgContent);
//
//    }

    @GetMapping("/test")
    public void updateCaseStatus() {

//        caseServiceImpl.updateCaseStatusById(1, TestCaseStatus.NORMAL);
      Integer res=  caseServiceImpl.findApi("GET","https://api.btstu.cn","");
        System.out.println("find result:" + Integer.toString(res));
    }

}
