package com.tramshedtech.eventmanagement.entity;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class Bookings {
    private Integer id;
    private String roomNumber;
    private String roomName;
    private String postcode;
    private String location;
    private Date startDate;
    private Time startTime;
    private Date endDate;
    private Time endTime;
    /**
     * capacity of the room
     */
    private Integer roomCapacity;
    /**
     * status 0 off the shelf 1 on the shelf
     */
    private Integer status;
    private String message;
    private String img;
    private String av;
    private String payment;
    private Integer del;
}
