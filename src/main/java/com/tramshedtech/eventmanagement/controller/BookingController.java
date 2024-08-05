package com.tramshedtech.eventmanagement.controller;

import com.tramshedtech.eventmanagement.entity.Bookings;
import com.tramshedtech.eventmanagement.service.BookingService;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/getRoom")
    public ResponseEntity<List<String>> getRoom() {
        List<String> addresses = bookingService.getAllRoomName();
        if (addresses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(addresses);
    }
}
