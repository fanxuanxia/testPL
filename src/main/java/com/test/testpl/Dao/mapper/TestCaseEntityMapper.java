package com.test.testpl.Dao.mapper;

import com.test.testpl.Dao.entity.TestCaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TestCaseEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestCaseEntity record);

    int insertSelective(TestCaseEntity record);

    TestCaseEntity selectByPrimaryKey(Integer id);
    List<TestCaseEntity> selectByStatus(String status);

    int updateByPrimaryKeySelective(TestCaseEntity record);

    int updateByPrimaryKey(TestCaseEntity record);
    int updateStatus(@Param("id") Integer id, @Param("status") String status);
}