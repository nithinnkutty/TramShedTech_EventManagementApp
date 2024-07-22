package com.tramshedtech.eventmanagement.entity;

import lombok.Data;

@Data
public class ParticipantSpeaker {
    private int id;
    private String name;
    private String email;
    private String company;
    private String role; // Speaker, Panelist, etc.
    private String status; // Invited, Confirmed, Declined
}
