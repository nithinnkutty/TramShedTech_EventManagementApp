package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.entity.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PositionMapper {

    @Select("SELECT * FROM position WHERE id = #{id}")
    Position findById(int id);

}
