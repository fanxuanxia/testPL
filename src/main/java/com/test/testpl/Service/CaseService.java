package com.test.testpl.Service;

import com.test.testpl.Dao.dto.ExecuteResultDto;
import com.test.testpl.Dao.dto.TestCaseDto;
import com.test.testpl.Dao.entity.ExecutionHistoryRecordEntity;
import com.test.testpl.Dao.entity.TestCaseEntity;
import com.test.testpl.common.enums.TestCaseStatus;
import com.test.testpl.common.reponse.BaseResponse;
import com.test.testpl.common.request.HttpSamplerSettings;

import java.util.List;
import java.util.Map;

public interface CaseService {
//    ApiDefinition getCaseById(int id);

    TestCaseEntity getCaseById(int id);
    List<TestCaseEntity> getAllValidCase();
    List<TestCaseDto> getAllCaseAndExecuteHistory();
    TestCaseDto getExecuteHistoryByCaseId(Integer caseId);
    Integer insertCase(TestCaseEntity testCase);

    Map<String,Integer> addCase(TestCaseDto testCaseDto);
    Boolean addCaseByMsg(String msgString);
//    Boolean NoticeCaseFinished(ExecutionHistoryRecordEntity executionHistoryRecord);
    Integer delectCaseById(Integer caseId);
    ExecuteResultDto executeCase(Integer caseId);

    ExecutionHistoryRecordEntity createHistoryInstance(HttpSamplerSettings settings, Integer caseId, Integer apiId);

    Integer updateCaseStatusById(Integer caseId, TestCaseStatus status);

    Integer findApi(String method, String domain,String url);

}
