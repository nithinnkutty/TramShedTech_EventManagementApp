package com.tramshedtech.eventmanagement.Service;

import com.tramshedtech.eventmanagement.entity.Event;
import com.tramshedtech.eventmanagement.entity.EventSchedule;
import com.tramshedtech.eventmanagement.mapper.EventMapper;
import com.tramshedtech.eventmanagement.mapper.EventScheduleMapper;
import com.tramshedtech.eventmanagement.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


public class EventServiceTest {

    @Mock
    private EventMapper eventMapper;

    @Mock
    private EventScheduleMapper eventScheduleMapper;

    @InjectMocks
    private EventServiceImpl eventService;

    private Event event1;
    private Event event2;
    private List<Event> mockEvents;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        event1 = new Event();
        event1.setId(1L);
        event1.setTitle("Event 1");
        event1.setDel(1);
        event1.setSchedules(Arrays.asList(new EventSchedule()));

        event2 = new Event();
        event2.setId(2L);
        event2.setTitle("Event 2");
        event2.setDel(1);
        event2.setSchedules(Arrays.asList(new EventSchedule()));

        mockEvents = Arrays.asList(event1, event2);
    }

    @Test
    public void testGetAllEvents() {
        when(eventMapper.getAllEvents()).thenReturn(mockEvents);
        when(eventScheduleMapper.getSchedulesByEventId(1L)).thenReturn(event1.getSchedules());
        when(eventScheduleMapper.getSchedulesByEventId(2L)).thenReturn(event2.getSchedules());

        List<Event> result = eventService.getAllEvents();

        assertEquals(2, result.size());
        assertEquals("Event 1", result.get(0).getTitle());
        assertEquals("Event 2", result.get(1).getTitle());
    }

    @Test
    public void testGetEventById() {
        when(eventMapper.getEventById(1L)).thenReturn(event1);
        when(eventScheduleMapper.getSchedulesByEventId(1L)).thenReturn(event1.getSchedules());

        Event result = eventService.getEventById(1L);

        assertNotNull(result);
        assertEquals("Event 1", result.getTitle());
        assertEquals(event1.getSchedules(), result.getSchedules());
    }

    @Test
    public void testAddEvent() {
        when(eventMapper.add(event1)).thenReturn(true);

        Long eventId = eventService.addEvent(event1);

        assertEquals(1, eventId);
    }

    @Test
    public void testSoftDeleteEvent() {
        when(eventMapper.softDelete(anyLong())).thenReturn(1);

        int result = eventService.softDeleteEvent(1L);

        assertEquals(1, result);
    }
}
