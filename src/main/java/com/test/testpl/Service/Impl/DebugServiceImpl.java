package com.test.testpl.Service.Impl;

import cn.hutool.core.lang.hash.Hash;
import com.test.testpl.Dao.entity.ApiDefinitionEntity;
import com.test.testpl.Dao.mapper.ApiDefinitionEntityMapper;
import com.test.testpl.Service.DebugService;
import com.test.testpl.common.reponse.SingleResult;
import com.test.testpl.common.request.HttpSamplerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dsig.SignedInfo;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class DebugServiceImpl implements DebugService {
    @Autowired
    ApiDefinitionEntityMapper apiDefinitionEntityMapper;

    @Autowired
    private JmeterClientImpl jmeterClient;

    @Override
    public Map<String, Integer> saveApi(ApiDefinitionEntity apiInfo){
         int res = apiDefinitionEntityMapper.insert(apiInfo);
         if(res==1){
             //插入成功，返回新增的自增id.
            return  SingleResult.create("apiId",apiInfo.getId());
         }
         return SingleResult.create("ApiId",-1);
    }


    @Override
    public void debug(HttpSamplerSettings settings) throws IOException {
        jmeterClient.runTestPlan(settings);
    }
}
