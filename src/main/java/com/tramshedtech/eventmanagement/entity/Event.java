package com.tramshedtech.eventmanagement.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Event {

    private Long id;
    private String title;
    private String date;
    private String time;
    private String room;
    private String tag;
    private String speaker;
    private String responsible;
    // Table data fields
    private String eventType;
    private String staff;
    private String company;
    private String contact;
    private String email;
    private String site;
    private String av;
    private String note;

}
