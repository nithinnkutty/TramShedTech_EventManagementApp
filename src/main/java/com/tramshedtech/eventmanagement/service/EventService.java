package com.tramshedtech.eventmanagement.service;

import com.tramshedtech.eventmanagement.entity.Event;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents();

    Event getEventById(Long id);

    Long addEvent(Event event);

    String getAllRoomName(int eventId);

    List<String> getAllEventTitles();

    int softDeleteEvent(Long id);

    boolean updateEvent(Event event);
}
