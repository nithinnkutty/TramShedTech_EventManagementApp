package com.tramshedtech.eventmanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileMapper {
    Boolean upload(@Param("uid")int uid, @Param("url")String url);
}
