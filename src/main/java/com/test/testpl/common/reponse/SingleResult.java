package com.test.testpl.common.reponse;

import java.util.HashMap;
import java.util.Map;

public class SingleResult {
        public static Map<String, Integer> create(String key, Integer value) {
            Map<String, Integer> map = new HashMap<>();
            map.put(key, value);
            return map;
        }
}
