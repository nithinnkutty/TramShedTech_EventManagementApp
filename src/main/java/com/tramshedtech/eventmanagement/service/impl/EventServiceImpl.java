package com.tramshedtech.eventmanagement.service.impl;

import com.tramshedtech.eventmanagement.entity.Event;
import com.tramshedtech.eventmanagement.entity.EventSchedule;
import com.tramshedtech.eventmanagement.mapper.EventMapper;
import com.tramshedtech.eventmanagement.mapper.EventScheduleMapper;
import com.tramshedtech.eventmanagement.service.EventService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class EventServiceImpl implements EventService {

    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    @Resource
    private EventMapper eventMapper;

    @Resource
    private EventScheduleMapper eventScheduleMapper;

    @Override
    public List<Event> getAllEvents() {
        // Retrieve all events that are not marked as deleted (del = 1)
        List<Event> events = eventMapper.getAllEvents().stream()
                .filter(event -> event.getDel() == 1)
                .collect(Collectors.toList());
        // For each event, retrieve its associated schedules and set them in the event object
        for (Event event : events) {
            List<EventSchedule> schedules = eventScheduleMapper.getSchedulesByEventId(event.getId());
            event.setSchedules(schedules);
        }
        return events;
    }

    @Override
    public Event getEventById(Long id) {
        try {
            Event event = eventMapper.getEventById(id);
            if (event != null) {
                List<EventSchedule> schedules = eventScheduleMapper.getSchedulesByEventId(id);
                event.setSchedules(schedules);
            }
            return event;
        } catch (Exception e) {
            // Log the error with event ID and the exception details
            System.err.println("Error fetching event ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long addEvent(Event event) {
        // Log event creation attempt
        log.info("Attempting to add event: " + event.getTitle());

        boolean result = eventMapper.add(event);
        if (result) {
            for (EventSchedule schedule : event.getSchedules()) {
                schedule.setEventId(event.getId());
                eventScheduleMapper.insertEventSchedule(schedule);
            }
            // Log successful event creation
            log.info("Event added successfully with ID: " + event.getId());
            return event.getId();
        } else {
            // Log event creation failure
            log.error("Failed to add event: " + event.getTitle());
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
