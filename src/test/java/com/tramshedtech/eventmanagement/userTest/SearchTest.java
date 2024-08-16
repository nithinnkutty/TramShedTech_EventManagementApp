package com.tramshedtech.eventmanagement.userTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

import java.util.Collections;

import com.tramshedtech.eventmanagement.controller.UserController;
import com.tramshedtech.eventmanagement.entity.CustomPage;
import com.tramshedtech.eventmanagement.entity.User;
import com.tramshedtech.eventmanagement.service.UserService;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
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

@WebMvcTest(UserController.class)
public class SearchTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearch() throws Exception {
        // Setting the return result of a simulation
        CustomPage mockPage = new CustomPage().setCurrentPage(1).setSize(10);
        when(userService.search(mockPage, new User())).thenReturn(mockPage);

        mockMvc.perform(get("/user/search")
                        .param("account", "testAccount")
                        .param("did", "testDid")
                        .param("pid", "testPid")
                        .param("sex", "M")
                        .param("entrydate", "2024-08-15")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name()));
    }
}

