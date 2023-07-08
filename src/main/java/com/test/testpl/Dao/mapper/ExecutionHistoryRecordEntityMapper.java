package com.test.testpl.Dao.mapper;

import com.test.testpl.Dao.entity.ExecutionHistoryRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExecutionHistoryRecordEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExecutionHistoryRecordEntity record);
    List<ExecutionHistoryRecordEntity> selectByCaseId(Integer caseId);

    int insertSelective(ExecutionHistoryRecordEntity record);

    ExecutionHistoryRecordEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExecutionHistoryRecordEntity record);

    int updateByPrimaryKeyWithBLOBs(ExecutionHistoryRecordEntity record);

    int updateByPrimaryKey(ExecutionHistoryRecordEntity record);
}