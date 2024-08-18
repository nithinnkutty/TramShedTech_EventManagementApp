package com.tramshedtech.eventmanagement.controller;

import com.tramshedtech.eventmanagement.entity.Event;
import com.tramshedtech.eventmanagement.entity.ParticipantSpeaker;
import com.tramshedtech.eventmanagement.service.EventService;
import com.tramshedtech.eventmanagement.service.ParticipantSpeakerService;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/participants-speakers")
public class ParticipantSpeakerController {
    @Resource
    private ParticipantSpeakerService participantSpeakerService;

    @Resource
    private EventService eventService;

    private static final Logger logger = LoggerFactory.getLogger(ParticipantSpeakerController.class);

    @GetMapping
    public ResponseResult<List<ParticipantSpeaker>> getAll() {
        List<ParticipantSpeaker> list = participantSpeakerService.getAll();
        return new ResponseResult<List<ParticipantSpeaker>>()
                .setCode(200)
                .setStatus(ResponseStatus.SUCCESS)
                .setData(list);
    }

    @GetMapping("/{id}")
    public ResponseResult<ParticipantSpeaker> getById(@PathVariable int id) {
        ParticipantSpeaker participantSpeaker = participantSpeakerService.getById(id);
        if (participantSpeaker == null) {
            return new ResponseResult<ParticipantSpeaker>()
                    .setCode(404)
                    .setStatus(ResponseStatus.FAIL)
                    .setMessage("Participant/Speaker not found");
        }
        return new ResponseResult<ParticipantSpeaker>()
                .setCode(200)
                .setStatus(ResponseStatus.SUCCESS)
                .setData(participantSpeaker);
    }

    @GetMapping("/events")
    public ResponseResult<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllUpcomingEvents();
        return new ResponseResult<List<Event>>()
                .setCode(200)
                .setStatus(ResponseStatus.SUCCESS)
                .setData(events);
    }

    @PostMapping
    public ResponseResult<Boolean> insert(@RequestBody ParticipantSpeaker participantSpeaker) {
        logger.info("Storing eventDateTime: {}", participantSpeaker.getEventDateTime());
        boolean success = participantSpeakerService.insert(participantSpeaker);
        return new ResponseResult<Boolean>()
                .setCode(success ? 200 : 500)
                .setStatus(success ? ResponseStatus.SUCCESS : ResponseStatus.FAIL)
                .setData(success);
    }

    @PutMapping("/{id}")
    public ResponseResult<Boolean> update(@PathVariable int id, @RequestBody ParticipantSpeaker participantSpeaker) {
        participantSpeaker.setId(id);
        boolean success = participantSpeakerService.update(participantSpeaker);
        return new ResponseResult<Boolean>()
                .setCode(success ? 200 : 500)
                .setStatus(success ? ResponseStatus.SUCCESS : ResponseStatus.FAIL)
                .setData(success);
    }

    @DeleteMapping("/{id}")
    public ResponseResult<Boolean> delete(@PathVariable int id) {
        boolean success = participantSpeakerService.delete(id);
        return new ResponseResult<Boolean>()
                .setCode(success ? 200 : 500)
                .setStatus(success ? ResponseStatus.SUCCESS : ResponseStatus.FAIL)
                .setData(success);
    }

    @PostMapping("/{id}/publish")
    public ResponseResult<String> publish(@PathVariable int id) {
        String publishUrl = participantSpeakerService.publish(id);
        return new ResponseResult<String>()
                .setCode(200)
                .setStatus(ResponseStatus.SUCCESS)
                .setData(publishUrl);
    }
}
