package com.test.testpl.Dao.entity;

public class AssertRuleEntity {
    private Integer ruleId;

    private Integer apiExecutionId;

    private String assertType;

    private String expectedValue;
    private String expression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }


    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getApiExecutionId() {
        return apiExecutionId;
    }

    public void setApiExecutionId(Integer apiExecutionId) {
        this.apiExecutionId = apiExecutionId;
    }

    public String getAssertType() {
        return assertType;
    }

    public void setAssertType(String assertType) {
        this.assertType = assertType == null ? null : assertType.trim();
    }

    public String getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(String expectedValue) {
        this.expectedValue = expectedValue == null ? null : expectedValue.trim();
    }
}