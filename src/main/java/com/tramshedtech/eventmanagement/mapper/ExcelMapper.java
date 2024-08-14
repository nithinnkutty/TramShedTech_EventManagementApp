package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.entity.UserExcel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ExcelMapper {

    /**
     * New data
     *
     * @param ofexcel instance object
     * @return Number of lines affected
     */
    int insert(UserExcel ofexcel);
}
