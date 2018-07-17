package com.madhu.flightcheckin.integration;

import com.madhu.flightcheckin.integration.dto.Reservation;
import com.madhu.flightcheckin.integration.dto.ReservationUpdateRequest;

public interface ReservationRestClient {

	public Reservation findReservation(Long id);

	public Reservation updateReservation(ReservationUpdateRequest request);

}
