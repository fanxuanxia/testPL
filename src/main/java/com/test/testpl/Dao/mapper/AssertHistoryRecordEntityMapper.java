package com.test.testpl.Dao.mapper;

import com.test.testpl.Dao.entity.AssertHistoryRecordEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssertHistoryRecordEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AssertHistoryRecordEntity record);

    int insertSelective(AssertHistoryRecordEntity record);

    AssertHistoryRecordEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AssertHistoryRecordEntity record);

    int updateByPrimaryKey(AssertHistoryRecordEntity record);
}