package com.rafael.challenge.receiver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafael.challenge.receiver.dto.WebhookEventRequest;
import com.rafael.challenge.receiver.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReceiveWebhookEvent() throws Exception {
        WebhookEventRequest request = new WebhookEventRequest();
        request.setEvent("order.created");
        request.setOrderId("123");
        request.setStoreId("store-1");
        request.setTimestamp(Instant.now().toString()); // ✅ obrigatório

        doNothing().when(eventService).processEvent(request);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
