package com.test.testpl.Dao.entity;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;

import java.util.Map;

public class TestCaseEntity {
    private Integer id;

    private String name;

    private String description;

    private String priority;

    private String relativeApi;

    private String status;

    private String headers;
    private  String resquestBody;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHeaders() {
        return headers;
    }

    public Map<String,String> getMapHeaders(){
        if(headers !=null) {
            return JSON.parseObject(headers, new TypeReference<Map<String, String>>() {
            });
        }else return null;

    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getResquestBody() {
        return resquestBody;
    }

    public void setResquestBody(String resquestBody) {
        this.resquestBody = resquestBody;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority == null ? null : priority.trim();
    }

    public String getRelativeApi() {
        return relativeApi;
    }

    public void setRelativeApi(String relativeApi) {
        this.relativeApi = relativeApi == null ? null : relativeApi.trim();
    }

}