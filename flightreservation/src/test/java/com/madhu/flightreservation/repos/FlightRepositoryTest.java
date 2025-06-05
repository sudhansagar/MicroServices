package com.madhu.flightreservation.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.madhu.flightreservation.entities.Flight;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DisplayName("FlightRepository Test")
class FlightRepositoryTest {

    private static final String FROM = "NYC";
    private static final String TO = "LAX";
    private static final Date DEPARTURE_DATE =
            new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime();

    @Autowired
    private FlightRepository repository;

    @Test
    @DisplayName("findFlights returns flights matching query")
    void findFlightsReturnsMatches() {
        Flight flight = new Flight();
        flight.setDepartureCity(FROM);
        flight.setArrivalCity(TO);
        flight.setDateOfDeparture(DEPARTURE_DATE);
        repository.save(flight);

        Flight other = new Flight();
        other.setDepartureCity("DEL");
        other.setArrivalCity("MUM");
        other.setDateOfDeparture(DEPARTURE_DATE);
        repository.save(other);

        List<Flight> results = repository.findFlights(FROM, TO, DEPARTURE_DATE);

        assertEquals(1, results.size());
        assertEquals(FROM, results.get(0).getDepartureCity());
    }
}
