package com.tramshedtech.eventmanagement.controller;

import com.tramshedtech.eventmanagement.Vo.BookingsVo;
import com.tramshedtech.eventmanagement.entity.Bookings;
import com.tramshedtech.eventmanagement.service.BookingService;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.sql.Time;

@Controller
@RequestMapping("/booking")
@ResponseBody
public class BookingController {

    @Resource
    private BookingService bookingService;

    @PostMapping("/add")
    public ResponseResult addBooking (@RequestBody Map<String, Object> bookingData) throws ParseException {

        String firstName = (String) bookingData.get("firstName");
        String lastName = (String) bookingData.get("lastName");
        String email = (String) bookingData.get("email");
        String location = (String) bookingData.get("location");
        String[] dateRange = ((List<String>) bookingData.get("daterange")).toArray(new String[0]);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        Date startDate = dateFormat.parse(dateRange[0].substring(0, 10));
        Time startTime = new Time(timeFormat.parse(dateRange[0].substring(11)).getTime());

        Date endDate = dateFormat.parse(dateRange[1].substring(0, 10));
        Time endTime = new Time(timeFormat.parse(dateRange[1].substring(11)).getTime());

        String av = String.join(",", (List<String>) bookingData.get("av"));
        String payment = (String) bookingData.get("payment");
        String message = (String) bookingData.get("message");

        Bookings booking = new Bookings();
        booking.setFirstName(firstName);
        booking.setLastName(lastName);
        booking.setEmail(email);
        booking.setLocation(location);
        booking.setStartDate(startDate);
        booking.setStartTime(startTime);
        booking.setEndDate(endDate);
        booking.setEndTime(endTime);
        booking.setAv(av);
        booking.setPayment(payment);
        booking.setMessage(message);
        booking.setDel(0);

        boolean r = bookingService.addBooking(booking);
        ResponseResult responseResult = new ResponseResult();
        if (r) {
            responseResult.setCode(200);
            responseResult.setMessage("Booking successful");
            responseResult.setData(r);
        } else {
            responseResult.setCode(400);
            responseResult.setMessage("Booking failed");
        }
        return responseResult;
    }

    @GetMapping("/searchAll")
    public List<BookingsVo> searchAll () throws ParseException {
        List<BookingsVo> bookings = bookingService.searchAll();
//        System.out.println(bookings);
        return bookings;
    }

    @GetMapping("/{id}")
    public ResponseResult getBooking(@PathVariable Integer id) {
        Bookings booking = bookingService.getBookingById(id);
        if (booking != null) {
            ResponseResult responseResult = new ResponseResult();
            responseResult.setCode(200);
            responseResult.setMessage("Booking fetched successfully");
            responseResult.setData(booking);
            return responseResult;
        } else {
            ResponseResult responseResult = new ResponseResult();
            responseResult.setCode(404);
            responseResult.setMessage("Booking not found");
            return responseResult;
        }
    }

    @PostMapping("/update")
    public ResponseResult updateBooking(@RequestBody Map<String, Object> bookingData) throws ParseException {

        Integer id = (Integer) bookingData.get("id");
        String firstName = (String) bookingData.get("firstName");
        String lastName = (String) bookingData.get("lastName");
        String email = (String) bookingData.get("email");
        String location = (String) bookingData.get("location");
        String[] dateRange = ((List<String>) bookingData.get("daterange")).toArray(new String[0]);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        Date startDate = dateFormat.parse(dateRange[0].substring(0, 10));
        Time startTime = new Time(timeFormat.parse(dateRange[0].substring(11)).getTime());

        Date endDate = dateFormat.parse(dateRange[1].substring(0, 10));
        Time endTime = new Time(timeFormat.parse(dateRange[1].substring(11)).getTime());

        String av = String.join(",", (List<String>) bookingData.get("av"));
        String payment = (String) bookingData.get("payment");
        String message = (String) bookingData.get("message");

        Bookings booking = new Bookings();
        booking.setId(id);
        booking.setFirstName(firstName);
        booking.setLastName(lastName);
        booking.setEmail(email);
        booking.setLocation(location);
        booking.setStartDate(startDate);
        booking.setStartTime(startTime);
        booking.setEndDate(endDate);
        booking.setEndTime(endTime);
        booking.setAv(av);
        booking.setPayment(payment);
        booking.setMessage(message);
        booking.setDel(0);

        boolean r = bookingService.updateBooking(booking);
        ResponseResult responseResult = new ResponseResult();
        if (r) {
            responseResult.setCode(200);
            responseResult.setMessage("Booking updated successfully");
            responseResult.setData(booking);
        } else {
            responseResult.setCode(400);
            responseResult.setMessage("Booking update failed");
        }
        return responseResult;
    }

}
