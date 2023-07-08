package com.test.testpl.Service.Impl;

import com.alibaba.fastjson2.JSON;
import com.test.testpl.Dao.dto.ExecuteResultDto;
import com.test.testpl.Dao.dto.TestCaseDto;
import com.test.testpl.Dao.entity.ApiDefinitionEntity;
import com.test.testpl.Dao.entity.ExecutionHistoryRecordEntity;
import com.test.testpl.Dao.entity.TestCaseEntity;
import com.test.testpl.Dao.mapper.ApiDefinitionEntityMapper;
import com.test.testpl.Dao.mapper.ExecutionHistoryRecordEntityMapper;
import com.test.testpl.Dao.mapper.TestCaseEntityMapper;
import com.test.testpl.Service.CaseService;
import com.test.testpl.Service.DebugService;
import com.test.testpl.common.enums.TestCaseStatus;
import com.test.testpl.common.reponse.BaseResponse;
import com.test.testpl.common.reponse.SingleResult;
import com.test.testpl.common.request.HttpSamplerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CaseServiceImpl implements CaseService {
    @Autowired
    private TestCaseEntityMapper testCaseEntityMapper;
    @Autowired
    private ExecutionHistoryRecordEntityMapper executionHistoryRecordMapper;

    @Autowired
    ApiDefinitionEntityMapper apiDefinitionEntityMapper;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private DebugService debugService;

    @Override
    public TestCaseEntity getCaseById(int id) {
        TestCaseEntity a = testCaseEntityMapper.selectByPrimaryKey(id);
        return a;
    }

    @Override
    public List<TestCaseEntity> getAllValidCase() {
        List<TestCaseEntity> testCase = new ArrayList<>();
        testCase.addAll(testCaseEntityMapper.selectByStatus(TestCaseStatus.NORMAL.getCode()));
        testCase.addAll(testCaseEntityMapper.selectByStatus(TestCaseStatus.EXECUTING.getCode()));
        return  testCase;
    }

    @Override
    public List<TestCaseDto> getAllCaseAndExecuteHistory() {
        //先查询所有的用例
        List<TestCaseEntity> testCases = getAllValidCase();
        //构造结果
        List<TestCaseDto> result = new ArrayList<TestCaseDto>();
        //对所有的用例进行遍历，查询历史记录、接口信息。
        for (TestCaseEntity testCase : testCases) {
            Integer caseId = testCase.getId();
            //查历史记录
            List<ExecutionHistoryRecordEntity> executionHistory = executionHistoryRecordMapper.selectByCaseId(caseId);
            TestCaseDto temp = new TestCaseDto();
            temp.setTestCase(testCase);
            temp.setExecutionHistoryRecord(executionHistory);
            result.add(temp);
        }
        return result;
    }

    @Override
    public TestCaseDto getExecuteHistoryByCaseId(Integer caseId) {
        //构造结果
        TestCaseDto result = new TestCaseDto();
        List<ExecutionHistoryRecordEntity> executionHistory = executionHistoryRecordMapper.selectByCaseId(caseId);
        result.setExecutionHistoryRecord(executionHistory);
        return result;
    }

    @Override
    public  Integer insertCase(TestCaseEntity testCase){
        return  testCaseEntityMapper.insert(testCase);
    }

    @Override
    public Map<String,Integer> addCase(TestCaseDto testCaseDto){
        ApiDefinitionEntity apiDefinition = testCaseDto.getApiDefinition();
        Integer apiId = findApi(apiDefinition.getMethod(), apiDefinition.getDomain(),apiDefinition.getUrl());
        TestCaseEntity testCase = testCaseDto.getTestCase();
        //RelativeApi  这个字段存啥呢？没想好，先保存接口的id 个接口path吧。
        if(apiId!=-1){
            testCase.setRelativeApi(Integer.toString(apiId));
            testCase.setStatus(TestCaseStatus.NORMAL.getCode());
            insertCase(testCase);
            return SingleResult.create("caseId",testCase.getId());
        }else return null;
    }
    @Override
    public Integer findApi(String method, String domain,String url){
        ApiDefinitionEntity api = apiDefinitionEntityMapper.findApi(method,domain,url);
        if(api != null){
            return api.getId();
        }
        return -1;
    }

    @Override
    public  Integer delectCaseById(Integer caseId){
        return testCaseEntityMapper.deleteByPrimaryKey(caseId);
    }

    @Override
    public ExecuteResultDto executeCase(Integer caseId){
        System.out.println("caseId: " +caseId);
        TestCaseEntity caseInfo = testCaseEntityMapper.selectByPrimaryKey(caseId);
        ExecuteResultDto resultDto = new ExecuteResultDto();
        if(caseInfo!=null){
            HttpSamplerSettings  settings = new HttpSamplerSettings();
            Integer apiId =Integer.parseInt(caseInfo.getRelativeApi());
            if(apiId ==-1){
                resultDto.setStatus("Failure");
                System.out.println("执行到2");
                return resultDto;
            }
            ApiDefinitionEntity apiInfo = apiDefinitionEntityMapper.selectByPrimaryKey(apiId);
            settings.setMethod(apiInfo.getMethod());
            settings.setDomain(apiInfo.getDomain());
            settings.setPath(apiInfo.getUrl());
            if(caseInfo.getMapHeaders()!=null) {
                settings.setHeaders(caseInfo.getMapHeaders());
            }
            if(caseInfo.getResquestBody()!=null) {
                settings.setRequestBody(caseInfo.getResquestBody());
            }
            try {
                debugService.debug(settings);
                Map<String, Integer> stringIntegerMap = addHistory(createHistoryInstance(settings, caseId, apiId));
                updateCaseStatusById(caseId,TestCaseStatus.EXECUTING);
                resultDto.setCaseId(caseId);
                resultDto.setExecuteId(stringIntegerMap.get("hisroty"));
                resultDto.setStatus(TestCaseStatus.EXECUTING.getCode());
                return resultDto;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            resultDto.setStatus("Failure");
            System.out.println("执行到1");
            return resultDto;
        }
    }
    @Override
    public ExecutionHistoryRecordEntity createHistoryInstance(HttpSamplerSettings settings, Integer caseId,Integer apiId){
        ExecutionHistoryRecordEntity res = new ExecutionHistoryRecordEntity();
        res.setCaseId(caseId);
        res.setRequestHeaders(JSON.toJSONString(settings.getHeaders()));
        res.setRequestBody(settings.getRequestBody());
        res.setApiDefinitionId(apiId);
        res.setExecutor("Tony");
        return  res;
    }
    @Override
    public Integer updateCaseStatusById(Integer caseId, TestCaseStatus status){
        Integer res = testCaseEntityMapper.updateStatus(caseId,status.getCode());
        if(res !=0){
            System.out.println("[INFO] 用例状态修改成功了");
        }else{
            System.out.println("[INFO] 用例状态修改失败了");
        }
        return res;
    }
    @Override
    public Boolean addCaseByMsg(String msgString){
        ListenableFuture listenableFuture = kafkaTemplate.send("test", msgString);
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

        return res;
    }

//    @KafkaListener(topics = "CaseFinished")
//    public void  listenMsg(String msgContent){
//        System.out.println("监听到消息：" + msgContent);
//        ExecutionHistoryRecordEntity executionHistoryRecord = (ExecutionHistoryRecordEntity)JSON.toJSON(msgContent);
//        addHistory(executionHistoryRecord);
//    }

    private Map<String,Integer> addHistory(ExecutionHistoryRecordEntity executionHistoryRecord){
        Integer res= executionHistoryRecordMapper.insert(executionHistoryRecord);
        return SingleResult.create("hisroty",executionHistoryRecord.getId());

    }


}
