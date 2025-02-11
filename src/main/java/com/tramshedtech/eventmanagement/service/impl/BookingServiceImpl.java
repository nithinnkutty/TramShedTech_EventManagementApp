package com.tramshedtech.eventmanagement.service.impl;

import com.tramshedtech.eventmanagement.Vo.BookingsVo;
import com.tramshedtech.eventmanagement.entity.Bookings;
import com.tramshedtech.eventmanagement.mapper.BookingMapper;
import com.tramshedtech.eventmanagement.mapper.UserMapper;
import com.tramshedtech.eventmanagement.service.BookingService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.LongStream;

@Service
public class BookingServiceImpl implements BookingService {

    @Resource
    private BookingMapper bookingMapper;

    @Override
    public boolean addBooking(Bookings book) {
        return bookingMapper.addBooking(book);
    }

    @Override
    public boolean softDeleteBooking(Integer id) {
        return bookingMapper.softDeleteBooking(id);
    }

    @Override
    public boolean updateBooking(Bookings book) {
        return bookingMapper.updateBooking(book);
    }

    @Override
    public boolean updateBookingStatus(Integer id, Integer status) {
        return bookingMapper.updateBookingStatus(id,status);
    }

    @Override
    public List<BookingsVo> searchNotCancel() {
        return bookingMapper.searchNotCancel();
    }

    @Override
    public Bookings getBookingById(Integer id) {
        return bookingMapper.getBookingById(id);
    }

    @Override
    public List<BookingsVo> searchAll(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Bookings> bookings = bookingMapper.searchAll();
        List<BookingsVo> bookingsVos = new ArrayList<>();

        for (Bookings booking : bookings) {

            String startDateString = dateFormat.format(booking.getStartDate());
            String endDateString = dateFormat.format(booking.getEndDate());

            BookingsVo bookingsVo = new BookingsVo();

            bookingsVo.setId(booking.getId());
            bookingsVo.setRoomNumber(booking.getRoomNumber());
            bookingsVo.setRoomName(booking.getRoomName());
            bookingsVo.setPostcode(booking.getPostcode());
            bookingsVo.setLocation(booking.getLocation());
            bookingsVo.setLocationName(booking.getLocationName());
            bookingsVo.setStartDate(startDateString);
            bookingsVo.setStartTime(booking.getStartTime());
            bookingsVo.setEndDate(endDateString);
            bookingsVo.setEndTime(booking.getEndTime());
            bookingsVo.setStatus(booking.getStatus());
            bookingsVo.setRoomCapacity(booking.getRoomCapacity());
            bookingsVo.setPostcode(booking.getPostcode());
            bookingsVo.setMessage(booking.getMessage());
            bookingsVo.setImg(booking.getImg());
            bookingsVo.setAv(booking.getAv());
            bookingsVo.setPayment(booking.getPayment());
            bookingsVo.setDel(booking.getDel());

            bookingsVos.add(bookingsVo);
        }
        return bookingsVos;
    }

    @Override
    public List<String> getAllRoomName(){return bookingMapper.getAllRoomName();}

    @Override
    public List<Bookings> getRoomsByLocationId(int locationId) {
        return bookingMapper.findRoomsByLocationId(locationId);
    }
}
