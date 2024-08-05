package com.tramshedtech.eventmanagement.service;

import com.tramshedtech.eventmanagement.entity.Bookings;

import java.util.List;

public interface BookingService {
    boolean addBooking(Bookings book);

    List<String> getAllRoomName();
}
