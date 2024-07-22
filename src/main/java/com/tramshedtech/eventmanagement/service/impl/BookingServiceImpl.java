package com.tramshedtech.eventmanagement.service.impl;

import com.tramshedtech.eventmanagement.entity.Bookings;
import com.tramshedtech.eventmanagement.mapper.BookingMapper;
import com.tramshedtech.eventmanagement.mapper.UserMapper;
import com.tramshedtech.eventmanagement.service.BookingService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    @Resource
    private BookingMapper bookingMapper;

    @Override
    public boolean addBooking(Bookings book) {
        return bookingMapper.addBooking(book);
    }
}
