package com.tramshedtech.eventmanagement.entity;

import lombok.Data;
import java.util.List;

@Data
public class Event {

    private Long id;
    private String title;
    private Integer location;
    private Integer room;
    private String tag;
    private String speaker;
    private String responsible;
    private Integer del;

    private String roomName;
    private String locationName;
    private String img;

    // Table data fields
    private String type;
    private String staff;
    private String company;
    private String contact;
    private String email;
    private String note;

    // Fields for catering
    private String cateringType;
    private int cateringCount;
    private String beverageType;
    private String dietaryRequirements;
    private String cateringServingTime;

    private List<EventSchedule> schedules; // New field for multiple dates and time slots

}
