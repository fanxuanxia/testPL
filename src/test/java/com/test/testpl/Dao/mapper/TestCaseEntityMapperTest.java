package com.test.testpl.Dao.mapper;

import com.test.testpl.Dao.entity.TestCaseEntity;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
@MapperScan({"com.test.testpl.Dao.mapper"})
class TestCaseEntityMapperTest {
    @Autowired
    TestCaseEntityMapper testCaseEntityMapper;

    @Test
    void insert() {
        TestCaseEntity test= new TestCaseEntity();
        test.setId(0000001);
        test.setName("第一个测试用例");
        test.setDescription("添加用例描述");
        test.setPriority("1");
        test.setRelativeApi("www.baidu.com");
        testCaseEntityMapper.insert(test);

    }
}