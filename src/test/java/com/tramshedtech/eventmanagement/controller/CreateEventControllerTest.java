package com.tramshedtech.eventmanagement.controller;

import com.tramshedtech.eventmanagement.entity.Event;
import com.tramshedtech.eventmanagement.entity.Bookings;
import com.tramshedtech.eventmanagement.service.EventService;
import com.tramshedtech.eventmanagement.service.BookingService;
import com.tramshedtech.eventmanagement.service.FeedbackService;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
public class CreateEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private FeedbackService feedbackService;

    private List<Bookings> mockBookings;

    private Event event;
    @BeforeEach
    void setUp() {
        event = new Event();
        event.setId(1L);
        event.setTitle("TramshedtechEvent");
        event.setLocation(1);
        event.setRoom(1);
        event.setRoomName("The Crossing-Tramshed Tech, Grangetown-CF24 2SO");
        event.setLocationName("Tramshed Tech, Grangetown");
        event.setType("Networking");
        event.setSpeaker("Tom");
        event.setTag("Networking");
        event.setResponsible("Tommy");
        event.setCompany("Tramshed Tech");
        event.setContact("78945613256");
        event.setEmail("tom@tramshedtech.com");
        event.setNote("No");

        Bookings booking1 = new Bookings();
        booking1.setId(1);
        booking1.setRoomName("The Butler-Tramshed Tech Griffin Place, Newport-CF24 7SO");
        booking1.setLocation(1);

        Bookings booking2 = new Bookings();
        booking2.setId(2);
        booking2.setRoomName("The Boiler Room-Tramshed Tech Barry, GoodSheds-CF18 5AN");
        booking2.setLocation(1);

        Bookings booking3 = new Bookings();
        booking3.setId(3);
        booking3.setRoomName("Ground Floor (Whole Site)-Tramshed Tech Barry, GoodSheds-CF24 8SO");
        booking3.setLocation(1);

        mockBookings = Arrays.asList(booking1, booking2, booking3);
    }
    @Test
    public void testGetAllEvents() throws Exception {
        List<Event> events = Arrays.asList(event);

        when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(get("/api/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name()))
                .andExpect(jsonPath("$.message").value("Events retrieved successfully"))
                .andExpect(jsonPath("$.data[0].title").value("TramshedtechEvent"));
    }

    @Test
    public void testGetEventById() throws Exception {
        when(eventService.getEventById(anyLong())).thenReturn(event);

        mockMvc.perform(get("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name()))
                .andExpect(jsonPath("$.message").value("Event retrieved successfully"))
                .andExpect(jsonPath("$.data.title").value("TramshedtechEvent"));
    }

    @Test
    public void testCreateEvent() throws Exception {
        when(eventService.addEvent(any(Event.class))).thenReturn(1L);

        mockMvc.perform(post("/api/events/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Sample Event\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name()))
                .andExpect(jsonPath("$.message").value("Event created successfully"))
                .andExpect(jsonPath("$.data").value(1L));
    }

    @Test
    public void testGetRoomsByLocation() throws Exception {
        when(bookingService.getRoomsByLocationId(anyInt())).thenReturn(mockBookings);

        mockMvc.perform(get("/api/events/rooms/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomName").value("The Butler-Tramshed Tech Griffin Place, Newport-CF24 7SO"))
                .andExpect(jsonPath("$[1].roomName").value("The Boiler Room-Tramshed Tech Barry, GoodSheds-CF18 5AN"))
                .andExpect(jsonPath("$[2].roomName").value("Ground Floor (Whole Site)-Tramshed Tech Barry, GoodSheds-CF24 8SO"));
    }

    @Test
    public void testGetAllRoomName() throws Exception {
        when(eventService.getAllRoomName(any(Integer.class))).thenReturn("The Crossing-Tramshed Tech, Grangetown-CF24 2SO");

        mockMvc.perform(get("/api/events/getRoom/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("The Crossing-Tramshed Tech, Grangetown-CF24 2SO"));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        when(eventService.softDeleteEvent(anyLong())).thenReturn(1);

        mockMvc.perform(post("/api/events/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name()))
                .andExpect(jsonPath("$.message").value("Event deleted successfully"));
    }

    @Test
    public void testUpdateEvent() throws Exception {
        when(eventService.updateEvent(any(Event.class))).thenReturn(true);

        mockMvc.perform(put("/api/events/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"TramshedTechConcert\", \"location\": 1, \"room\": 1, \"type\": \"Festival\", \"speaker\": \"Tom\", \"tag\": \"Festival\", \"responsible\": \"Tommy\", \"company\": \"Tramshed Tech\", \"contact\": \"78945613256\", \"email\": \"tom@tramshedtech.com\", \"note\": \"No\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name()))
                .andExpect(jsonPath("$.message").value("Event updated successfully"));
    }
}