package com.tramshedtech.eventmanagement.entity;

import lombok.Data;

@Data
public class Event {
    private Long id;
    private String title;
    private String date;
    private String time;
    private String site;
    private String room;
}

