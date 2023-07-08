package com.test.testpl.common.utils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetApiConfigs {
    private String FilePath = "src/main/resources/TestNG/TestNg-apiConfig.xml";

    public String getFilePath() {
        return FilePath;
    }

    public Map<String,Map<String,String>> AnalyzeConfig(){
        Map<String,Map<String,String>> res = new HashMap<>();
        Map<String,String> headersMap = new HashMap<>();
        Map<String,String> paramsMap = new HashMap<>();
        SAXReader reader = new SAXReader();
        try {
            //根据路径读取文件
            Document doc = reader.read(FilePath);
            Element element = doc.getRootElement();
            String rootURL = element.element("baseURL").getTextTrim();
            Map<String,String> temp = new HashMap<>();
            temp.put("baseURL",rootURL);
            res.put("baseURL",temp);

            //解析headers参数
            List<Element> headersEle = element.element("headers").elements("head");
            headersEle.forEach(ele -> {
                String key = ele.attributeValue("name");
                String value = ele.attributeValue("value");
                headersMap.put(key,value);
                res.put("headers",headersMap);
            });

            //解析params参数
            List<Element> ParamsEle = element.element("bodyParams").elements("bodyParam");
            ParamsEle.forEach(ele -> {
                String key = ele.attributeValue("name");
                String value = ele.attributeValue("value");
                paramsMap.put(key,value);
                res.put("params",paramsMap);
            });
            return res;
        }catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

}
