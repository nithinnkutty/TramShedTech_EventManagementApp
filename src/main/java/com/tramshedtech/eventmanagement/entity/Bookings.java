package com.tramshedtech.eventmanagement.entity;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class Bookings {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String location;
    private Date startDate;
    private Time startTime;
    private Date endDate;
    private Time endTime;
    private String address;
    private String postcode;
    private String status;
    private String message;
    private String av;
    private String payment;
}
