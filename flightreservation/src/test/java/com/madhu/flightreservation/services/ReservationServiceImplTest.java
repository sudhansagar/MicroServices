package com.madhu.flightreservation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.madhu.flightreservation.dto.ReservationRequest;
import com.madhu.flightreservation.entities.Flight;
import com.madhu.flightreservation.entities.Passenger;
import com.madhu.flightreservation.entities.Reservation;
import com.madhu.flightreservation.repos.FlightRepository;
import com.madhu.flightreservation.repos.PassengerRepository;
import com.madhu.flightreservation.repos.ReservationRepository;
import com.madhu.flightreservation.utils.EmailUtil;
import com.madhu.flightreservation.utils.PDFGenerator;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReservationServiceImpl Test")
class ReservationServiceImplTest {

    private static final long FLIGHT_ID = 1L;
    private static final long RESERVATION_ID = 10L;
    private static final String ITINERARY_DIR = "/tmp/";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String EMAIL = "john@example.com";
    private static final String PHONE = "1234567890";

    @Mock
    private FlightRepository flightRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private PDFGenerator pdfGenerator;
    @Mock
    private EmailUtil emailUtil;

    private ReservationServiceImpl service;

    private ReservationRequest request;
    private Flight flight;
    private Passenger savedPassenger;
    private Reservation savedReservation;

    @BeforeEach
    void setUp() {
        request = new ReservationRequest();
        request.setFlightId(FLIGHT_ID);
        request.setPassengerFirstName(FIRST_NAME);
        request.setPassengerLastName(LAST_NAME);
        request.setPassengerEmail(EMAIL);
        request.setPassengerPhone(PHONE);

        flight = new Flight();
        flight.setId(FLIGHT_ID);

        savedPassenger = new Passenger();
        savedPassenger.setId(2L);
        savedPassenger.setFirstName(FIRST_NAME);
        savedPassenger.setLastName(LAST_NAME);
        savedPassenger.setEmail(EMAIL);
        savedPassenger.setPhone(PHONE);

        savedReservation = new Reservation();
        savedReservation.setId(RESERVATION_ID);
        savedReservation.setPassenger(savedPassenger);
        savedReservation.setFlight(flight);
        savedReservation.setCheckedIn(false);

        service = new ReservationServiceImpl(
                flightRepository,
                passengerRepository,
                reservationRepository,
                pdfGenerator,
                emailUtil,
                ITINERARY_DIR);
    }

    @Test
    @DisplayName("bookFlight should persist reservation and send itinerary")
    void bookFlightShouldPersistReservationAndSendItinerary() {
        when(flightRepository.findById(FLIGHT_ID)).thenReturn(Optional.of(flight));
        when(passengerRepository.save(any(Passenger.class))).thenReturn(savedPassenger);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);

        Reservation result = service.bookFlight(request);

        assertSame(savedReservation, result);

        ArgumentCaptor<Passenger> passengerCaptor = ArgumentCaptor.forClass(Passenger.class);
        verify(passengerRepository).save(passengerCaptor.capture());
        Passenger capturedPassenger = passengerCaptor.getValue();
        assertEquals(FIRST_NAME, capturedPassenger.getFirstName());
        assertEquals(LAST_NAME, capturedPassenger.getLastName());
        assertEquals(EMAIL, capturedPassenger.getEmail());
        assertEquals(PHONE, capturedPassenger.getPhone());

        ArgumentCaptor<Reservation> reservationCaptor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationRepository).save(reservationCaptor.capture());
        Reservation capturedReservation = reservationCaptor.getValue();
        assertSame(flight, capturedReservation.getFlight());
        assertSame(savedPassenger, capturedReservation.getPassenger());
        assertFalse(capturedReservation.getCheckedIn());

        String expectedPath = ITINERARY_DIR + RESERVATION_ID + ".pdf";
        verify(pdfGenerator).generateItinerary(savedReservation, expectedPath);
        verify(emailUtil).sendItinerary(EMAIL, expectedPath);
    }

    @Test
    @DisplayName("bookFlight throws exception when flight not found")
    void bookFlightShouldThrowWhenFlightMissing() {
        when(flightRepository.findById(FLIGHT_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.bookFlight(request));

        verifyNoInteractions(passengerRepository, reservationRepository, pdfGenerator, emailUtil);
    }
}

