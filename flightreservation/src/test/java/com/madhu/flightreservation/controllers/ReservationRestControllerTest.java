package com.madhu.flightreservation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.madhu.flightreservation.dto.ReservationUpdateRequest;
import com.madhu.flightreservation.entities.Reservation;
import com.madhu.flightreservation.repos.ReservationRepository;

@WebMvcTest(ReservationRestController.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("ReservationRestController Test")
class ReservationRestControllerTest {

    private static final long RESERVATION_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationRepository repository;

    @Test
    @DisplayName("findReservation returns reservation")
    void findReservationReturnsReservation() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setId(RESERVATION_ID);
        when(repository.findById(RESERVATION_ID)).thenReturn(Optional.of(reservation));

        mockMvc.perform(get("/reservations/{id}", RESERVATION_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(RESERVATION_ID));
    }

    @Test
    @DisplayName("updateReservation updates reservation")
    void updateReservationUpdatesReservation() throws Exception {
        ReservationUpdateRequest req = new ReservationUpdateRequest();
        req.setId(RESERVATION_ID);
        req.setCheckedIn(true);
        req.setNumberOfBags(2);

        Reservation reservation = new Reservation();
        reservation.setId(RESERVATION_ID);
        when(repository.findById(RESERVATION_ID)).thenReturn(Optional.of(reservation));
        when(repository.save(any(Reservation.class))).thenReturn(reservation);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(RESERVATION_ID));

        verify(repository).save(reservation);
    }
}
