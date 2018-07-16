package com.madhu.flightreservation.services;

import com.madhu.flightreservation.dto.ReservationRequest;
import com.madhu.flightreservation.entities.Reservation;

public interface ReservationService {
	
	public Reservation bookFlight(ReservationRequest request);

}
