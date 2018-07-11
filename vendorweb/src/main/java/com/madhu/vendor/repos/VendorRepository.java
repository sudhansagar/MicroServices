package com.madhu.vendor.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madhu.vendor.entities.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

}
