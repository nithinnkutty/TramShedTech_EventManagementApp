package com.tramshedtech.eventmanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ParticipantSpeaker {
    private int id;
    private String name;
    private String email;
    private String company;
    private String status; // Invited, Confirmed, Declined
    private Long eventId; // Foreign key to events table
    private String eventName;
    private String relationshipWithCompany;
    private String bio;
    private String  eventDateTime;
    private String role; // Speaker, Panelist, etc.
    private Long scheduleId;
}
