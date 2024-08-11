package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.entity.EventSchedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventScheduleMapper {
    int insertEventSchedule(EventSchedule eventSchedule);
    List<EventSchedule> getSchedulesByEventId(Long eventId);
}
