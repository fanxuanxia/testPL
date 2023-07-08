package com.test.testpl.common.request;

public class JMeterRequestResult {

    String Url;
    String ResponseSize;
    String ResponseTime;
    String ResponseResult;
    String ConsoleResult;

    String Cookie;
    String RequestMethod;

    String RequestData;
    String ResponseHeader;
    String RequestHeader;
    String StatusCode;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getResponseSize() {
        return ResponseSize;
    }

    public void setResponseSize(String responseSize) {
        ResponseSize = responseSize;
    }

    public String getResponseTime() {
        return ResponseTime;
    }

    public void setResponseTime(String responseTime) {
        ResponseTime = responseTime;
    }

    public String getResponseResult() {
        return ResponseResult;
    }

    public void setResponseResult(String responseResult) {
        ResponseResult = responseResult;
    }

    public String getConsoleResult() {
        return ConsoleResult;
    }

    public void setConsoleResult(String consoleResult) {
        ConsoleResult = consoleResult;
    }

    public String getCookie() {
        return Cookie;
    }

    public void setCookie(String cookie) {
        Cookie = cookie;
    }

    public String getRequestMethod() {
        return RequestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        RequestMethod = requestMethod;
    }

    public String getRequestData() {
        return RequestData;
    }

    public void setRequestData(String requestData) {
        RequestData = requestData;
    }

    public String getResponseHeader() {
        return ResponseHeader;
    }

    public void setResponseHeader(String responseHeader) {
        ResponseHeader = responseHeader;
    }

    public String getRequestHeader() {
        return RequestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        RequestHeader = requestHeader;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }
}
