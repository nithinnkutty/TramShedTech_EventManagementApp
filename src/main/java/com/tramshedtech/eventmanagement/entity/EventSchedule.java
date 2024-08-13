package com.tramshedtech.eventmanagement.entity;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class EventSchedule {
    private Long id;
    private Long eventId; // Foreign key to the Event table
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
