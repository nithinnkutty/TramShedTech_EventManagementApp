package com.tramshedtech.eventmanagement.service.impl;

import com.tramshedtech.eventmanagement.entity.ParticipantSpeaker;
import com.tramshedtech.eventmanagement.mapper.ParticipantSpeakerMapper;
import com.tramshedtech.eventmanagement.service.ParticipantSpeakerService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantSpeakerServiceImpl implements ParticipantSpeakerService {
    private static final Logger logger = LoggerFactory.getLogger(ParticipantSpeakerServiceImpl.class);

    @Resource
    private ParticipantSpeakerMapper participantSpeakerMapper;

    @Override
    public List<ParticipantSpeaker> getAll() {
        return participantSpeakerMapper.getAll();
    }

    @Override
    public ParticipantSpeaker getById(int id) {
        return participantSpeakerMapper.getById(id);
    }

    @Override
    public boolean insert(ParticipantSpeaker participantSpeaker) {
        String formattedEventDateTime = participantSpeaker.getEventDateTime();
        logger.info("Formatted eventDateTime before insert: {}", formattedEventDateTime);
        participantSpeaker.setEventDateTime(formattedEventDateTime);
        return participantSpeakerMapper.insert(participantSpeaker);
    }

    @Override
    public boolean update(ParticipantSpeaker participantSpeaker) {
        logger.debug("Updating ParticipantSpeaker with id: {}", participantSpeaker.getId());
        boolean result = participantSpeakerMapper.update(participantSpeaker);
        logger.debug("Update result: {}", result);
        return result;
    }

    @Override
    public boolean delete(int id) {
        return participantSpeakerMapper.delete(id);
    }

    @Override
    public String publish(int id) {
        ParticipantSpeaker participantSpeaker = participantSpeakerMapper.getById(id);
        if (participantSpeaker == null) {
            throw new RuntimeException("Participant/Speaker not found");
        }
        // Generate URL for published page (for simplicity, using a static base URL)
        String publishUrl = "http://yourdomain.com/participants-speakers/" + id;
        return publishUrl;
    }

    @Override
    public String getEventNameById(Long eventId) {
        return participantSpeakerMapper.getEventNameById(eventId);
    }

    @Override
    public String getEventDateTimeByEventId(Long eventId) {
        return participantSpeakerMapper.getEventDateTimeByEventId(eventId);
    }
}
