package com.tramshedtech.eventmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CustomPage implements Serializable {
    private static final long serialVersionUID = 1l;
    private int currentPage;        // 当前页
    private int size;               // 页大小
    private long total;             // 总条数
    private int totalPage;          // 总页数
    private Object pageData;        // 当前页数据
}
