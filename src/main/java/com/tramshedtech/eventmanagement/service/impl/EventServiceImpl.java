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
        if (r) {
            return event.getId();
        } else {
            // Add logging to capture the event object being inserted
            System.out.println("Failed to insert event: " + event);
            return 0L;
        }
    }

    @Override
    public List<String> getAllEventTitles() {
        return eventMapper.getAllEventTitles();
    }
}
