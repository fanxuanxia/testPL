package com.test.testpl.Service.Impl;

import com.alibaba.fastjson2.JSONObject;
import com.test.testpl.Dao.entity.ApiDefinitionEntity;

public class JSON2JavaTest {
    public static void main(String[] args) {
        ApiDefinitionEntity apiDefinitionEntity = new ApiDefinitionEntity();
        apiDefinitionEntity.setId(11111);
        apiDefinitionEntity.setName("lllll");

        String string = JSONObject.toJSONString(apiDefinitionEntity);
        System.out.println("result:" +string);
    }
}
