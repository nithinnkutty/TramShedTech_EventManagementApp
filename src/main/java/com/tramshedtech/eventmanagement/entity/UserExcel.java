package com.tramshedtech.eventmanagement.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserExcel implements Serializable {

    private Integer id;

    @ExcelProperty("Account")
    private String account;

    @ExcelProperty("Email")
    private String email;

    @ExcelProperty("Department")
    private String did;

    @ExcelProperty("Position")
    private String pid;

    @ExcelProperty("Gender")
    private String sex;

    @ExcelProperty("Entrydate")
    private Date entrydate;

    @ExcelProperty("Phone")
    private String phone;

    @ExcelProperty("Wechat")
    private String wechat;

    @ExcelProperty("Status")
    private Integer status;
}
