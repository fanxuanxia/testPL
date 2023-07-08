package com.test.testpl.common.request;

public class RequestHeader {
    private  String headerKey;
    private  String headerValue;

    public String getHeaderKey() {
        return this.headerKey;
    }

    public String getHeaderValue() {
        return this.headerValue;
    }

    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }
}
