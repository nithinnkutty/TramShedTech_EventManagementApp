package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.entity.Event;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EventMapper {

    @Select("SELECT * FROM events")
    List<Event> getAllEvents();

    @Select("SELECT * FROM events WHERE id = #{id}")
    Event getEventById(Long id);

}

