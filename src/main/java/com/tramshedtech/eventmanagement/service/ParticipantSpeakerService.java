package com.tramshedtech.eventmanagement.service;

import com.tramshedtech.eventmanagement.entity.ParticipantSpeaker;

import java.util.List;

public interface ParticipantSpeakerService {
    List<ParticipantSpeaker> getAll();
    ParticipantSpeaker getById(int id);
    boolean insert(ParticipantSpeaker participantSpeaker);
    boolean update(ParticipantSpeaker participantSpeaker);
    boolean delete(int id);
}
