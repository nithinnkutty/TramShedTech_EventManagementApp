package com.tramshedtech.eventmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTests {
    @Autowired
    private MockMvc mockMvc;

    /**
     * Find all rooms
     */
    @Test
    void testSearchAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/booking/searchAll").accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    void getBooking() throws Exception {
        String id = "39";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/booking/" + id).accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    void searchNotCancel() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/booking/searchNotCancel").accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    }

//    @Test
//    void getRoom() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/booking/getRoom").accept(MediaType.APPLICATION_JSON_VALUE)
//        ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
//    }

    @Test
    void testDeleteBooking() throws Exception {
        // Prepare the data for the request body
        Map<String, Integer> requestData = new HashMap<>();
        requestData.put("id", 39);
        // Convert the Map to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestData = objectMapper.writeValueAsString(requestData);
        // Perform the test
        mockMvc.perform(MockMvcRequestBuilders.post("/booking/delete")
                        .content(jsonRequestData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)) // 假设您的ResponseResult类中包含status字段
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Booking deleted successfully")) // 假设您的ResponseResult类中包含message字段
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void testUpdateBooking() throws Exception {
        // Prepare the data for the request body
        Map<String, Object> bookingData = new HashMap<>();
        bookingData.put("id", 2);
        bookingData.put("roomNumber", "Room 2");
        bookingData.put("roomName", "The Crossing");
        bookingData.put("postcode", "CF24 2SO");
        bookingData.put("location", 3);
        bookingData.put("daterange", new String[]{"2024-08-13T16:00:00.000Z", "2024-09-16T16:00:00.000Z"});
        bookingData.put("av", new String[]{});
        bookingData.put("payment", "");
        bookingData.put("message", "Seats up to 10 people.");
        bookingData.put("roomCapacity", 10);
        bookingData.put("img", "Barry Goodsheds template.png");
        bookingData.put("status", 1);

        // Convert the Map to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBookingData = objectMapper.writeValueAsString(bookingData);

        // Perform the test
        mockMvc.perform(MockMvcRequestBuilders.post("/booking/update")
                        .content(jsonBookingData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)) // 假设您的ResponseResult类中包含status字段
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Booking updated successfully")) // 假设您的ResponseResult类中包含message字段
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void updateStatus() throws Exception {
        // Prepare the data for the request body
        Map<String, Integer> requestData = new HashMap<>();
        requestData.put("id", 4);
        requestData.put("status", 0);
        // Convert the Map to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestData = objectMapper.writeValueAsString(requestData);
        // Perform the test
        mockMvc.perform(MockMvcRequestBuilders.post("/booking/updateStatus")
                        .content(jsonRequestData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)) // 假设您的ResponseResult类中包含status字段
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Booking update successfully")) // 假设您的ResponseResult类中包含message字段
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * add test
     *
     * @throws Exception
     */

    @Test
    void testAddBooking() throws Exception {
        // Prepare the data for the request body
        Map<String, Object> bookingData = new HashMap<>();
        bookingData.put("roomNumber", "111");
        bookingData.put("roomName", "111");
        bookingData.put("postcode", "111");
        bookingData.put("roomCapacity", 3);
        bookingData.put("location", 2);
        bookingData.put("daterange", new String[]{"2024-08-13T16:00:00.000Z", "2024-09-10T16:00:00.000Z"});
        bookingData.put("av", new String[]{"Microphone"});
        bookingData.put("payment", "Applepay");
        bookingData.put("message", "");
        bookingData.put("img", "2016101910270126.jpg");
        bookingData.put("startDate", "2024-08-13T16:00:00.000Z");
        bookingData.put("endDate", "2024-09-10T16:00:00.000Z");
        // Convert the Map to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBookingData = objectMapper.writeValueAsString(bookingData);
        // Perform the test
        mockMvc.perform(MockMvcRequestBuilders.post("/booking/add")
                        .content(jsonBookingData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)) // 假设您的ResponseResult类中包含status字段
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Booking successful")) // 假设您的ResponseResult类中包含message字段
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test file upload
     *
     * @throws Exception
     */
    @Test
    void testUploadFile() throws Exception {
        // Create mock file
        MockMultipartFile file = new MockMultipartFile(
                "file", // file name
                "test-image.jpg", // Client file name
                "image/jpeg", // MIME type
                "Hello, World!".getBytes() // file content
        );

        // Prepare the desired response results
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "File uploaded successfully");
        // Perform the test
        mockMvc.perform(multipart("/booking/upload")
                        .file(file)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("/uploads/test-image.jpg"))
                .andDo(MockMvcResultHandlers.print());
    }
}
