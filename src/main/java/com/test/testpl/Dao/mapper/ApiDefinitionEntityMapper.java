package com.test.testpl.Dao.mapper;

import com.test.testpl.Dao.entity.ApiDefinitionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ApiDefinitionEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApiDefinitionEntity record);

    int insertSelective(ApiDefinitionEntity record);

    ApiDefinitionEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApiDefinitionEntity record);

    int updateByPrimaryKey(ApiDefinitionEntity record);

    ApiDefinitionEntity findApi(@Param("method") String method,@Param("domain") String domain,@Param("url") String url );
}