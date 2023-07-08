package com.test.testpl.Dao.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExecutionHistoryRecordEntity {
    private Integer id;

    private Integer caseId;

    private Integer apiDefinitionId;

    private Date executeTime;

    private String executor;

    private Integer executeResult;

    private String requestHeaders;

    private String requestBody;

    private String responseCode;

    private String responseHeaders;

    private String responseBody;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getApiDefinitionId() {
        return apiDefinitionId;
    }

    public void setApiDefinitionId(Integer apiDefinitionId) {
        this.apiDefinitionId = apiDefinitionId;
    }

    public String getExecuteTime() {
        // 时间格式化一下吧
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = formatter.format(executeTime);
        return formattedDateTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor == null ? null : executor.trim();
    }

    public Integer getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(Integer executeResult) {
        this.executeResult =executeResult;
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders == null ? null : requestHeaders.trim();
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody == null ? null : requestBody.trim();
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode == null ? null : responseCode.trim();
    }

    public String getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders == null ? null : responseHeaders.trim();
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody == null ? null : responseBody.trim();
    }
}