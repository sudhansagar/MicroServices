package com.madhu.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madhu.flightreservation.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
