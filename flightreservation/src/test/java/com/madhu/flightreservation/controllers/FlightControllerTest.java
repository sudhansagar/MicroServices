package com.madhu.flightreservation.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.madhu.flightreservation.entities.Flight;
import com.madhu.flightreservation.repos.FlightRepository;

@WebMvcTest(FlightController.class)
@ExtendWith(SpringExtension.class)
@DisplayName("FlightController Test")
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightRepository repository;

    @Test
    @DisplayName("findFlights returns view with flights")
    void findFlightsReturnsViewWithFlights() throws Exception {
        when(repository.findFlights("NYC", "LAX", any(Date.class)))
                .thenReturn(Collections.singletonList(new Flight()));

        mockMvc.perform(get("/findFlights")
                .param("from", "NYC")
                .param("to", "LAX")
                .param("departureDate", "01-01-1970"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayFlights"))
                .andExpect(model().attributeExists("flights"));
    }
}
