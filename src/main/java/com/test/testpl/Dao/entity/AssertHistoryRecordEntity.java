package com.test.testpl.Dao.entity;

public class AssertHistoryRecordEntity {
    private Integer id;

    private Integer ruleId;

    private String actualVaule;

    private String assertResult;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getActualVaule() {
        return actualVaule;
    }

    public void setActualVaule(String actualVaule) {
        this.actualVaule = actualVaule == null ? null : actualVaule.trim();
    }

    public String getAssertResult() {
        return assertResult;
    }

    public void setAssertResult(String assertResult) {
        this.assertResult = assertResult == null ? null : assertResult.trim();
    }
}