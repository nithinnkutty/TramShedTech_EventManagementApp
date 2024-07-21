package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.entity.Event;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EventMapper {
    void add(Event event);
}
