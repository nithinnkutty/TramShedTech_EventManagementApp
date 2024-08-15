package com.tramshedtech.eventmanagement.userTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

import com.tramshedtech.eventmanagement.Vo.UserVo;
import com.tramshedtech.eventmanagement.controller.UserController;
import com.tramshedtech.eventmanagement.entity.User;
import com.tramshedtech.eventmanagement.service.UserService;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testLoginSuccess() throws Exception {
        User mockUser = new User();
        mockUser.setAccount("testAccount");
        mockUser.setPassword("testPassword");

        User foundUser = new User();
        foundUser.setAccount("testAccount");
        foundUser.setPassword(DigestUtils.md5Hex("testPassword"));

        when(userService.findByAccount("testAccount")).thenReturn(foundUser);

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.status").value(ResponseStatus.LOGIN_SUCCESS.name()))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    public void testLoginFailedDueToIncorrectPassword() throws Exception {
        User mockUser = new User();
        mockUser.setAccount("testAccount");
        mockUser.setPassword("wrongPassword");

        User foundUser = new User();
        foundUser.setAccount("testAccount");
        foundUser.setPassword(DigestUtils.md5Hex("correctPassword"));

        when(userService.findByAccount("testAccount")).thenReturn(foundUser);

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.message").value("Incorrect account number or password"))
                .andExpect(jsonPath("$.status").value(ResponseStatus.ACCOUNT_OR_PASSWORD_ERROR.name()));
    }


    @Test
    public void testLoginFailedDueToNonExistentAccount() throws Exception {
        User mockUser = new User();
        mockUser.setAccount("nonExistentAccount");
        mockUser.setPassword("anyPassword");

        when(userService.findByAccount("nonExistentAccount")).thenReturn(null);

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.message").value("Incorrect account number or password"))
                .andExpect(jsonPath("$.status").value(ResponseStatus.ACCOUNT_OR_PASSWORD_ERROR.name()));
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        User newUser = new User();
        newUser.setAccount("newUser");
        newUser.setPassword("password123");
        newUser.setEmail("newuser@example.com");

        when(userService.allUsers()).thenReturn(new ArrayList<>());

        when(userService.regis(newUser)).thenReturn(true);

        mockMvc.perform(post("/user/regis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterFailDueToDuplicateAccount() throws Exception {
        User newUser = new User();
        newUser.setAccount("duplicateUser");
        newUser.setPassword("password123");
        newUser.setEmail("unique@example.com");

        User existingUser = new User();
        existingUser.setAccount("duplicateUser");
        existingUser.setEmail("other@example.com");

        // Simulation returns a list containing existing users, triggering account duplicates
        List<User> existingUsers = new ArrayList<>();
        existingUsers.add(existingUser);
        when(userService.allUsers()).thenReturn(existingUsers);

        mockMvc.perform(post("/user/regis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.status").value(ResponseStatus.ACCOUNT_OR_PASSWORD_ERROR.name()));
    }

    @Test
    public void testRegisterFailDueToDuplicateEmail() throws Exception {
        User newUser = new User();
        newUser.setAccount("uniqueUser");
        newUser.setPassword("password123");
        newUser.setEmail("duplicate@example.com");

        User existingUser = new User();
        existingUser.setAccount("otherUser");
        existingUser.setEmail("duplicate@example.com");

        // Simulate the return of a list containing existing subscribers to trigger mailbox duplication
        List<User> existingUsers = new ArrayList<>();
        existingUsers.add(existingUser);
        when(userService.allUsers()).thenReturn(existingUsers);

        mockMvc.perform(post("/user/regis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.status").value(ResponseStatus.EMAIL_ERROR.name()));
    }

    @Test
    public void testAddUserSuccess() throws Exception {
        // Creating a simulated UserVo object
        UserVo mockUserVo = new UserVo();
        mockUserVo.setAccount("testAccount");
        mockUserVo.setPassword("testPassword");
        mockUserVo.setSex("1"); // Male

        // Simulating userService.addUser() returns true
        when(userService.addUser(mockUserVo)).thenReturn(true);

        // Send a POST request and validate the response
        mockMvc.perform(post("/user/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUserVo)))
                .andExpect(status().isOk());
    }
    @Test
    public void testAddUserFailure() throws Exception {
        // Creating a simulated UserVo object
        UserVo mockUserVo = new UserVo();
        mockUserVo.setAccount("testAccount");
        mockUserVo.setPassword("testPassword");
        mockUserVo.setSex("1"); // Male

        // Simulation of userService.addUser() returns false
        when(userService.addUser(mockUserVo)).thenReturn(false);

        // Send a POST request and validate the response
        mockMvc.perform(post("/user/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUserVo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

}
