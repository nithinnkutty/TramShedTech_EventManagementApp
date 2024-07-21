package com.tramshedtech.eventmanagement.service;

import com.tramshedtech.eventmanagement.entity.Event;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents();

    Event getEventById(Long id);

    void addEvent(Event event);
}
