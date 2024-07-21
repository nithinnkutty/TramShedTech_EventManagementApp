package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.entity.Bookings;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookingMapper {
    boolean addBooking(Bookings book);
}
