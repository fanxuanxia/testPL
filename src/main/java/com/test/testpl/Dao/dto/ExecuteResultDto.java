package com.test.testpl.Dao.dto;

public class ExecuteResultDto {
    private Integer CaseId;
    private Integer ExecuteId;
    private String status;

    public Integer getCaseId() {
        return CaseId;
    }

    public void setCaseId(Integer caseId) {
        CaseId = caseId;
    }

    public Integer getExecuteId() {
        return ExecuteId;
    }

    public void setExecuteId(Integer executeId) {
        ExecuteId = executeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
