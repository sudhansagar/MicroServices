package com.madhu.flightreservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.madhu.flightreservation.dto.ReservationRequest;
import com.madhu.flightreservation.entities.Flight;
import com.madhu.flightreservation.entities.Passenger;
import com.madhu.flightreservation.entities.Reservation;
import com.madhu.flightreservation.repos.FlightRepository;
import com.madhu.flightreservation.repos.PassengerRepository;
import com.madhu.flightreservation.repos.ReservationRepository;
import com.madhu.flightreservation.utils.EmailUtil;
import com.madhu.flightreservation.utils.PDFGenerator;

@Service
public class ReservationServiceImpl implements ReservationService {

        private final FlightRepository flightRepository;
        private final PassengerRepository passengerRepository;
        private final ReservationRepository reservationRepository;
        private final PDFGenerator pdfGenerator;
        private final EmailUtil emailUtil;
        private final String itineraryDir;

        @Autowired
        public ReservationServiceImpl(
                        FlightRepository flightRepository,
                        PassengerRepository passengerRepository,
                        ReservationRepository reservationRepository,
                        PDFGenerator pdfGenerator,
                        EmailUtil emailUtil,
                        @Value("${com.madhu.flightreservation.itinerary.dirpath}") String itineraryDir) {
                this.flightRepository = flightRepository;
                this.passengerRepository = passengerRepository;
                this.reservationRepository = reservationRepository;
                this.pdfGenerator = pdfGenerator;
                this.emailUtil = emailUtil;
                this.itineraryDir = itineraryDir;
        }

	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

	@Override
	@Transactional
	public Reservation bookFlight(ReservationRequest request) {

		LOGGER.info("Inside bookFlight()");
		// Make Payment

		Long flightId = request.getFlightId();
                LOGGER.info("Fetching  flight for flight id:" + flightId);
                Flight flight = flightRepository.findById(flightId)
                                .orElseThrow();

		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setPhone(request.getPassengerPhone());
		passenger.setEmail(request.getPassengerEmail());
		LOGGER.info("Saving the passenger:" + passenger);
		Passenger savedPassenger = passengerRepository.save(passenger);

		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);

		LOGGER.info("Saving the reservation:" + reservation);
		Reservation savedReservation = reservationRepository.save(reservation);

                String filePath = itineraryDir + savedReservation.getId() + ".pdf";
		LOGGER.info("Generating  the itinerary");
		pdfGenerator.generateItinerary(savedReservation, filePath);
		
		LOGGER.info("Emailing the Itinerary");
		emailUtil.sendItinerary(passenger.getEmail(), filePath);
		
		return savedReservation;
	}

}
