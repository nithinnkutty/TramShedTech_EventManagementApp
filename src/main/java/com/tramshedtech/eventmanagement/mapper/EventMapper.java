package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.entity.Event;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventMapper {

    List<Event> getAllEvents();

    Event getEventById(Long id);

    void add(Event event);
}