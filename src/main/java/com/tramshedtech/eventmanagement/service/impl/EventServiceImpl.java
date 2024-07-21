package com.tramshedtech.eventmanagement.service.impl;

import com.tramshedtech.eventmanagement.entity.Event;
import com.tramshedtech.eventmanagement.mapper.EventMapper;
import com.tramshedtech.eventmanagement.service.EventService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventMapper eventMapper;
    @Override
    public void addEvent(Event event) {
        eventMapper.add(event);
    }
}
