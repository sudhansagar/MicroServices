package com.madhu.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madhu.flightreservation.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
