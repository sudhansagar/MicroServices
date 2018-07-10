package com.madhu.location.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madhu.location.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

}
