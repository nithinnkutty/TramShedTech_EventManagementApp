package com.tramshedtech.eventmanagement.service.impl;

import com.tramshedtech.eventmanagement.entity.Event;
import com.tramshedtech.eventmanagement.mapper.EventMapper;
import com.tramshedtech.eventmanagement.service.EventService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Resource
    private EventMapper eventMapper;

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
        boolean r = eventMapper.add(event);
        if (r == true){
            return event.getId();
        } else {
            return 0L;
        }

    }

    @Override
    public String getAllRoomName(int eventId){return eventMapper.getRoomById(eventId);}
}
