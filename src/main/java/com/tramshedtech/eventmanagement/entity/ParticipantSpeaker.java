package com.tramshedtech.eventmanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

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
    private Date eventDateTime;
    private String role; // Speaker, Panelist, etc.
    private Long scheduleId;
}
