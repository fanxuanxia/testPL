package com.test.testpl.Dao.mapper;

import com.test.testpl.Dao.entity.AssertRuleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssertRuleEntityMapper {
    int deleteByPrimaryKey(Integer ruleId);

    int insert(AssertRuleEntity record);

    int insertSelective(AssertRuleEntity record);

    AssertRuleEntity selectByPrimaryKey(Integer ruleId);

    int updateByPrimaryKeySelective(AssertRuleEntity record);

    int updateByPrimaryKey(AssertRuleEntity record);
}