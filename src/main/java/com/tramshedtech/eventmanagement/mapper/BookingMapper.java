package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.Vo.BookingsVo;
import com.tramshedtech.eventmanagement.entity.Bookings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookingMapper {
    boolean addBooking(Bookings book);

    List<String> getAllRoomName();

    boolean updateBooking(Bookings book);

    boolean softDeleteBooking(Integer id);

    Bookings getBookingById(Integer id);

    List<Bookings> searchAll();

    boolean updateBookingStatus(@Param("id") Integer id, Integer status);

    List<BookingsVo> searchNotCancel();
}
