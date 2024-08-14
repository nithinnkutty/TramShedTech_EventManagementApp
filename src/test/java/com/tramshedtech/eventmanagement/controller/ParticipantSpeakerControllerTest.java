package com.tramshedtech.eventmanagement.controller;

import com.tramshedtech.eventmanagement.entity.ParticipantSpeaker;
import com.tramshedtech.eventmanagement.service.ParticipantSpeakerService;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ParticipantSpeakerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ParticipantSpeakerService participantSpeakerService;

    @InjectMocks
    private ParticipantSpeakerController participantSpeakerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(participantSpeakerController).build();
    }

    @Test
    public void testGetAll() throws Exception {
        ParticipantSpeaker ps1 = new ParticipantSpeaker();
        ps1.setId(1);
        ps1.setName("John Doe");

        ParticipantSpeaker ps2 = new ParticipantSpeaker();
        ps2.setId(2);
        ps2.setName("Jane Doe");

        List<ParticipantSpeaker> allSpeakers = Arrays.asList(ps1, ps2);

        when(participantSpeakerService.getAll()).thenReturn(allSpeakers);

        mockMvc.perform(get("/participants-speakers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(ResponseStatus.SUCCESS.toString())))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].name", is("John Doe")));
    }

    @Test
    public void testGetById() throws Exception {
        ParticipantSpeaker ps = new ParticipantSpeaker();
        ps.setId(1);
        ps.setName("John Doe");

        when(participantSpeakerService.getById(1)).thenReturn(ps);

        mockMvc.perform(get("/participants-speakers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(ResponseStatus.SUCCESS.toString())))
                .andExpect(jsonPath("$.data.name", is("John Doe")));
    }

    @Test
    public void testInsert() throws Exception {
        ParticipantSpeaker ps = new ParticipantSpeaker();
        ps.setName("John Doe");
        ps.setEmail("john@example.com");
        ps.setCompany("Example Corp");
        ps.setRole("Speaker");
        ps.setStatus("Invited");

        when(participantSpeakerService.insert(ps)).thenReturn(true);

        mockMvc.perform(post("/participants-speakers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\", \"email\": \"john@example.com\", \"company\": \"Example Corp\", \"role\": \"Speaker\", \"status\": \"Invited\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(ResponseStatus.SUCCESS.toString())));
    }

    @Test
    public void testDelete() throws Exception {
        when(participantSpeakerService.delete(1)).thenReturn(true);

        mockMvc.perform(delete("/participants-speakers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(ResponseStatus.SUCCESS.toString())));
    }
}
