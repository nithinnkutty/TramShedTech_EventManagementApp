package com.tramshedtech.eventmanagement.controller;

import com.tramshedtech.eventmanagement.entity.Bookings;
import com.tramshedtech.eventmanagement.entity.EventSchedule;
import com.tramshedtech.eventmanagement.service.BookingService;
import com.tramshedtech.eventmanagement.service.EventService;
import com.tramshedtech.eventmanagement.service.FeedbackService;
import com.tramshedtech.eventmanagement.entity.Event;
import com.tramshedtech.eventmanagement.entity.Feedback;
import jakarta.annotation.Resource;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseResult<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseResult<List<Event>>()
                .setStatus(ResponseStatus.SUCCESS)
                .setMessage("Events retrieved successfully")
                .setData(events);
    }

    @GetMapping("/{id}")
    public ResponseResult<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event != null) {
            return new ResponseResult<Event>()
                    .setStatus(ResponseStatus.SUCCESS)
                    .setMessage("Event retrieved successfully")
                    .setData(event);
        } else {
            return new ResponseResult<Event>()
                    .setStatus(ResponseStatus.FAIL)
                    .setMessage("Event not found")
                    .setData(null);
        }
    }

    @PostMapping("/create")
    public ResponseResult<Long> createEvent(@RequestBody Event event) {
        Long eventId = eventService.addEvent(event);
        if (eventId > 0) {
            return new ResponseResult<Long>()
                    .setStatus(ResponseStatus.SUCCESS)
                    .setMessage("Event created successfully")
                    .setData(eventId);
        } else {
            return new ResponseResult<Long>()
                    .setStatus(ResponseStatus.FAIL)
                    .setMessage("Event creation failed")
                    .setData(null);
        }
    }

    @GetMapping("/getRoom/{eventId}")
    public ResponseEntity<String> getAllRoomName(@PathVariable int eventId) {
        try {
            String roomName = eventService.getAllRoomName(eventId);
            return ResponseEntity.ok(roomName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving room name");
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseResult deleteEvent(@PathVariable Long id) {
        try {
            int result = eventService.softDeleteEvent(id);
            if (result > 0) {
                return new ResponseResult().setCode(200).setStatus(ResponseStatus.SUCCESS).setMessage("Event deleted successfully");
            } else {
                return new ResponseResult().setCode(500).setStatus(ResponseStatus.FAIL).setMessage("Event deletion failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(500).setStatus(ResponseStatus.FAIL).setMessage("An error occurred");
        }
    }

    @GetMapping("/rooms/{locationId}")
    public List<Bookings> getRoomsByLocation(@PathVariable int locationId) {
        System.out.println("locationId: " + locationId);
        return bookingService.getRoomsByLocationId(locationId);
    }

    @PutMapping("/{id}")
    public ResponseResult updateEvent(@PathVariable Long id, @RequestBody Event event) {
        event.setId(id);
        try {
            boolean success = eventService.updateEvent(event);
            if (success) {
                return new ResponseResult()
                        .setCode(200)
                        .setStatus(ResponseStatus.SUCCESS)
                        .setMessage("Event updated successfully");
            } else {
                return new ResponseResult()
                        .setCode(500)
                        .setStatus(ResponseStatus.FAIL)
                        .setMessage("Failed to update event");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult()
                    .setCode(500)
                    .setStatus(ResponseStatus.FAIL)
                    .setMessage("An error occurred while updating the event");
        }
    }

    @GetMapping("/titles")
    public ResponseResult<List<String>> getAllEventTitles() {
        List<String> eventTitles = eventService.getAllEventTitles();
        return new ResponseResult<List<String>>()
                .setStatus(ResponseStatus.SUCCESS)
                .setMessage("Event titles retrieved successfully")
                .setData(eventTitles);
    }

    @PostMapping("/{eventId}/feedback")
    public ResponseResult addFeedback(@PathVariable Long eventId, @RequestBody Feedback feedback) {
        feedback.setEventId(eventId);
        boolean success = feedbackService.addFeedback(feedback);
        return new ResponseResult().setCode(success ? 200 : 500).setStatus(success ? ResponseStatus.SUCCESS : ResponseStatus.FAIL);
    }

    @GetMapping("/{eventId}/feedback")
    public ResponseResult<List<Feedback>> getFeedbackByEventId(@PathVariable Long eventId) {
        List<Feedback> feedbackList = feedbackService.getFeedbackByEventId(eventId);
        return new ResponseResult<List<Feedback>>()
                .setStatus(ResponseStatus.SUCCESS)
                .setMessage("Feedback retrieved successfully")
                .setData(feedbackList);
    }

    @GetMapping("/{eventId}/schedules")
    public ResponseResult<List<EventSchedule>> getEventSchedules(@PathVariable Long eventId) {
        List<EventSchedule> schedules = eventService.getSchedulesByEventId(eventId);
        return new ResponseResult<List<EventSchedule>>()
                .setStatus(ResponseStatus.SUCCESS)
                .setMessage("Event schedules retrieved successfully")
                .setData(schedules);
    }


}
