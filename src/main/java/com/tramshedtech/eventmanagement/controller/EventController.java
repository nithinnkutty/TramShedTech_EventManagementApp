package com.tramshedtech.eventmanagement.controller;

import com.tramshedtech.eventmanagement.entity.Event;
import com.tramshedtech.eventmanagement.service.EventService;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
import jakarta.annotation.Resource;
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
}

