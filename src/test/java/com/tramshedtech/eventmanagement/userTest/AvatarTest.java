package com.tramshedtech.eventmanagement.userTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import com.tramshedtech.eventmanagement.controller.UserController;
import com.tramshedtech.eventmanagement.service.UserService;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class AvatarTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private MockHttpSession session;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        session = new MockHttpSession();
        session.setAttribute("uid", 1);
    }

    @Test
    public void testFindAvatarSuccess() throws Exception {
        // Simulate the behaviour of userService.findAvatar()
        when(userService.findAvatar(1)).thenReturn("avatarUrl");

        mockMvc.perform(get("/user/findAvatar").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name()))
                .andExpect(jsonPath("$.data").value("avatarUrl"));
    }

    @Test
    public void testFindAvatarFail() throws Exception {
        // Simulating the behaviour of userService.findAvatar() returning null
        when(userService.findAvatar(1)).thenReturn(null);

        mockMvc.perform(get("/user/findAvatar").session(session))
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.status").value(ResponseStatus.FAIL.name()));
    }
}
