package com.tramshedtech.eventmanagement.controller;

import com.tramshedtech.eventmanagement.service.EventService;

import com.tramshedtech.eventmanagement.entity.Event;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/event")
@ResponseBody
public class EventController {
    @Resource
    private EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<String> createEvent(@RequestBody Event event) {
        try{
            eventService.addEvent(event);
            return ResponseEntity.ok("Event created successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Failed to create event: " + e.getMessage());
        }
    }
}
