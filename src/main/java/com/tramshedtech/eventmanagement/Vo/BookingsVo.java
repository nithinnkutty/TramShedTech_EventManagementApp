package com.tramshedtech.eventmanagement.Vo;

import lombok.Data;
import org.simpleframework.xml.convert.Convert;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Data
public class BookingsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String location;
    private String startDate;
    private Time startTime;
    private String endDate;
    private Time endTime;
    private String postcode;
    private String status;
    private String message;
    private String av;
    private String payment;
    private Integer del;
}
