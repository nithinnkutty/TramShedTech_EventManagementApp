package com.tramshedtech.eventmanagement.service;

import com.tramshedtech.eventmanagement.Vo.BookingsVo;
import com.tramshedtech.eventmanagement.entity.Bookings;

import java.text.ParseException;
import java.util.List;


public interface BookingService {
    boolean addBooking(Bookings book);

    boolean updateBooking(Bookings book);

    boolean softDeleteBooking(Integer id);

    Bookings getBookingById(Integer id);

    List<BookingsVo> searchAll() throws ParseException;

    List<String> getAllRoomName();
}
