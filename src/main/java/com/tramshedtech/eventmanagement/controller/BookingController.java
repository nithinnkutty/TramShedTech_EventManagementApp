package com.tramshedtech.eventmanagement.controller;

import com.tramshedtech.eventmanagement.Vo.BookingsVo;
import com.tramshedtech.eventmanagement.entity.Bookings;
import com.tramshedtech.eventmanagement.service.BookingService;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.io.IOException;
import java.util.Map;


@Controller
@RequestMapping("/booking")
@ResponseBody
public class BookingController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Resource
    private BookingService bookingService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Obtain the original file name of the uploaded file and clear the path
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            // Create the path to the target file
            Path path = Paths.get(uploadDir + "/" + fileName);
            Files.createDirectories(path.getParent()); // 确保目录存在
            // Saves the file to the specified path
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//            // Build file URL
//            String fileUrl = path.toString();
            String fileUrl = "/uploads/" + fileName; // 返回相对路径
            // Create a response object
            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            // Return the response object
            return ResponseEntity.ok(response);
        } catch (IOException ex) {
            // Processing file upload error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseResult addBooking (@RequestBody Map<String, Object> bookingData) throws ParseException {

        String roomNumber = (String) bookingData.get("roomNumber");
        String roomName = (String) bookingData.get("roomName");
        String postcode = (String) bookingData.get("postcode");
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
//        String img = (String) bookingData.get("img");
        List<String> imgList = (List<String>) bookingData.get("img");
        String img = String.join(",", imgList); // 将 imgList 转换为逗号分隔的字符串

        Bookings booking = new Bookings();
        booking.setRoomNumber(roomNumber);
        booking.setRoomName(roomName);
        booking.setPostcode(postcode);
        booking.setLocation(location);
        booking.setStartDate(startDate);
        booking.setStartTime(startTime);
        booking.setEndDate(endDate);
        booking.setEndTime(endTime);
        booking.setAv(av);
        booking.setPayment(payment);
        booking.setMessage(message);
        booking.setImg(img);
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

    @PostMapping("/delete")
    public ResponseResult deleteBooking(@RequestBody Map<String, Integer> requestData) {
        Integer id = requestData.get("id");
        boolean result = bookingService.softDeleteBooking(id);
        ResponseResult responseResult = new ResponseResult();
        if (result) {
            responseResult.setCode(200);
            responseResult.setMessage("Booking deleted successfully");
        } else {
            responseResult.setCode(400);
            responseResult.setMessage("Booking deletion failed");
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
        String roomNumber = (String) bookingData.get("roomNumber");
        String roomName = (String) bookingData.get("roomName");
        String postcode = (String) bookingData.get("postcode");
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
//        String img = (String) bookingData.get("img");
        List<String> imgList = (List<String>) bookingData.get("img");
        String img = String.join(",", imgList); // 将 imgList 转换为逗号分隔的字符串

        Bookings booking = new Bookings();
        booking.setId(id);
        booking.setRoomNumber(roomNumber);
        booking.setRoomName(roomName);
        booking.setPostcode(postcode);
        booking.setLocation(location);
        booking.setStartDate(startDate);
        booking.setStartTime(startTime);
        booking.setEndDate(endDate);
        booking.setEndTime(endTime);
        booking.setAv(av);
        booking.setPayment(payment);
        booking.setMessage(message);
        booking.setImg(img);
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
