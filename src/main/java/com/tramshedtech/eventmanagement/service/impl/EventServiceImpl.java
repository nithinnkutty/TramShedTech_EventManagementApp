package com.tramshedtech.eventmanagement.service.impl;

import com.tramshedtech.eventmanagement.entity.Event;
import com.tramshedtech.eventmanagement.entity.EventSchedule;
import com.tramshedtech.eventmanagement.mapper.EventMapper;
import com.tramshedtech.eventmanagement.mapper.EventScheduleMapper;
import com.tramshedtech.eventmanagement.service.EventService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Resource
    private EventMapper eventMapper;

    @Resource
    private EventScheduleMapper eventScheduleMapper;

    @Override
    public List<Event> getAllEvents() {
        return eventMapper.getAllEvents();
    }

    @Override
    public Event getEventById(Long id) {
        return eventMapper.getEventById(id);
    }

    @Override
    public Long addEvent(Event event) {
        boolean result = eventMapper.add(event);
        if (result) {
            for (EventSchedule schedule : event.getSchedules()) {
                schedule.setEventId(event.getId());
                eventScheduleMapper.insertEventSchedule(schedule);
            }
            return event.getId();
        } else {
            return 0L;
        }
    }

    @Override
    public List<String> getAllEventTitles() {
        return eventMapper.getAllEventTitles();
    }

    @Override
    public String getAllRoomName(int eventId){ return eventMapper.getRoomById(eventId); }

    @Override
    public int softDeleteEvent(Long id) { return eventMapper.softDelete(id); }

    @Override
    public boolean updateEvent(Event event) {
        int result = eventMapper.update(event);
        return result > 0;
    }
}
