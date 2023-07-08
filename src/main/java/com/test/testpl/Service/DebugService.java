package com.test.testpl.Service;

import com.test.testpl.Dao.entity.ApiDefinitionEntity;
import com.test.testpl.common.reponse.SingleResult;
import com.test.testpl.common.request.HttpSamplerSettings;

import java.io.IOException;
import java.util.Map;

public interface DebugService {
    Map<String, Integer> saveApi(ApiDefinitionEntity apiInfo);

    void debug(HttpSamplerSettings settings) throws IOException;
}
