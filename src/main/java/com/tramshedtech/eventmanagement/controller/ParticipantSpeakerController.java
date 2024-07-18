package com.tramshedtech.eventmanagement.controller;

import com.tramshedtech.eventmanagement.entity.ParticipantSpeaker;
import com.tramshedtech.eventmanagement.service.ParticipantSpeakerService;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/participants-speakers")
public class ParticipantSpeakerController {
    @Resource
    private ParticipantSpeakerService participantSpeakerService;

    @GetMapping
    public ResponseResult<List<ParticipantSpeaker>> getAll() {
        List<ParticipantSpeaker> list = participantSpeakerService.getAll();
        return new ResponseResult<List<ParticipantSpeaker>>().setCode(200).setStatus(ResponseStatus.SUCCESS).setData(list);
    }

    @GetMapping("/{id}")
    public ResponseResult<ParticipantSpeaker> getById(@PathVariable int id) {
        ParticipantSpeaker participantSpeaker = participantSpeakerService.getById(id);
        return new ResponseResult<ParticipantSpeaker>().setCode(200).setStatus(ResponseStatus.SUCCESS).setData(participantSpeaker);
    }

    @PostMapping
    public ResponseResult<Boolean> insert(@RequestBody ParticipantSpeaker participantSpeaker) {
        boolean success = participantSpeakerService.insert(participantSpeaker);
        return new ResponseResult<Boolean>().setCode(success ? 200 : 500).setStatus(success ? ResponseStatus.SUCCESS : ResponseStatus.FAIL).setData(success);
    }

    @PutMapping
    public ResponseResult<Boolean> update(@RequestBody ParticipantSpeaker participantSpeaker) {
        boolean success = participantSpeakerService.update(participantSpeaker);
        return new ResponseResult<Boolean>().setCode(success ? 200 : 500).setStatus(success ? ResponseStatus.SUCCESS : ResponseStatus.FAIL).setData(success);
    }

    @DeleteMapping("/{id}")
    public ResponseResult<Boolean> delete(@PathVariable int id) {
        boolean success = participantSpeakerService.delete(id);
        return new ResponseResult<Boolean>().setCode(success ? 200 : 500).setStatus(success ? ResponseStatus.SUCCESS : ResponseStatus.FAIL).setData(success);
    }
}
