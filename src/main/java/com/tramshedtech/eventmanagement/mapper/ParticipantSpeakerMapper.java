package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.entity.ParticipantSpeaker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ParticipantSpeakerMapper {
    List<ParticipantSpeaker> getAll();
    ParticipantSpeaker getById(@Param("id") int id);
    boolean insert(ParticipantSpeaker participantSpeaker);
    boolean update(ParticipantSpeaker participantSpeaker);
    boolean delete(@Param("id") int id);

    String getEventNameById(@Param("eventId") Long eventId);
    String getEventDateTimeByEventId(@Param("eventId") Long eventId);
}
