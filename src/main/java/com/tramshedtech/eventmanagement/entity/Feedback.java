package com.tramshedtech.eventmanagement.entity;

import lombok.Data;

@Data
public class Feedback {
    private Long id;
    private Long eventId;
    private String name;
    private String email;
    private int rating;
    private String comments;
}
