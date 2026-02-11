package com.rafael.challenge.receiver.controller;

import com.rafael.challenge.receiver.model.ReceivedEvent;
import com.rafael.challenge.receiver.repository.ReceivedEventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventQueryController.class)
class EventQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceivedEventRepository repository;

    @Test
    void shouldReturnEventsByStore() throws Exception {
        ReceivedEvent event = new ReceivedEvent();
        event.setStoreId("store-1");
        event.setEvent("order.created");

        when(repository.findByStoreId("store-1"))
                .thenReturn(List.of(event));

        mockMvc.perform(get("/events/store/store-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].storeId").value("store-1"))
                .andExpect(jsonPath("$[0].event").value("order.created"));
    }
}
