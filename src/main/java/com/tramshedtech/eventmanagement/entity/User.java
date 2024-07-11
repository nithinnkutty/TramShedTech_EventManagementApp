package com.tramshedtech.eventmanagement.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String account;
    private String password;
    private String email;
    private int status;
    private int identity;
}
