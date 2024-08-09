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
    private Integer location;
    private Integer room;
    private String tag;
    private String speaker;
    private String responsible;
    private Integer del;
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

}
