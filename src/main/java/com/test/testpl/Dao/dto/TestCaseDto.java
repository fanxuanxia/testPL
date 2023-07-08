package com.test.testpl.Dao.dto;

import com.test.testpl.Dao.entity.*;

import java.util.List;

public class TestCaseDto{
    TestCaseEntity testCase;
    ApiDefinitionEntity apiDefinition;
    List<ExecutionHistoryRecordEntity> executionHistoryRecord;
    List<AssertRuleEntity> assertRuleLists;
    List<AssertHistoryRecordEntity> assertHistoryRecords;

    public TestCaseEntity getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCaseEntity testCase) {
        this.testCase = testCase;
    }

    public ApiDefinitionEntity getApiDefinition() {
        return apiDefinition;
    }

    public void setApiDefinition(ApiDefinitionEntity apiDefinition) {
        this.apiDefinition = apiDefinition;
    }

    public List<ExecutionHistoryRecordEntity> getExecutionHistoryRecord() {
        return executionHistoryRecord;
    }

    public void setExecutionHistoryRecord(List<ExecutionHistoryRecordEntity> executionHistoryRecord) {
        this.executionHistoryRecord = executionHistoryRecord;
    }

    public List<AssertRuleEntity> getAssertRuleLists() {
        return assertRuleLists;
    }

    public void setAssertRuleLists(List<AssertRuleEntity> assertRuleLists) {
        this.assertRuleLists = assertRuleLists;
    }

    public List<AssertHistoryRecordEntity> getAssertHistoryRecords() {
        return assertHistoryRecords;
    }

    public void setAssertHistoryRecords(List<AssertHistoryRecordEntity> assertHistoryRecords) {
        this.assertHistoryRecords = assertHistoryRecords;
    }
}
