package com.tramshedtech.eventmanagement.service;

import com.tramshedtech.eventmanagement.entity.Event;
import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    Event getEventById(Long id);
    Long addEvent(Event event);
    List<String> getAllEventTitles();

}
