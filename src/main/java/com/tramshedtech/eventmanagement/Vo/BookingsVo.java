package com.tramshedtech.eventmanagement.Vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Data
public class BookingsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String roomNumber;
    private String roomName;
    private String postcode;
    private String location;
    private String startDate;
    private Time startTime;
    private String endDate;
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
