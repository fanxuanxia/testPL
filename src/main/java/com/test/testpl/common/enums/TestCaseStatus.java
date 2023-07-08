package com.test.testpl.common.enums;

public enum TestCaseStatus {
    NORMAL("NORMAL","正常"),
    EXECUTING("EXECUTING","执行中"),
    DELETED("DELETED","已删除");

    private String description;
    private String code;

    TestCaseStatus(String code,String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }
}
