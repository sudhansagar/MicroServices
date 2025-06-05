package com.madhu.vendor.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.madhu.vendor.entities.Vendor;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DisplayName("VendorRepository Test")
class VendorRepositoryTest {

    private static final int VENDOR_ID = 1;
    private static final String VENDOR_NAME = "Repo Vendor";

    @Autowired
    private VendorRepository repository;

    @Test
    @DisplayName("save and find by id")
    void saveAndFindById() {
        Vendor vendor = new Vendor();
        vendor.setId(VENDOR_ID);
        vendor.setName(VENDOR_NAME);
        repository.save(vendor);

        Vendor result = repository.findById(VENDOR_ID).orElse(null);

        assertEquals(VENDOR_NAME, result.getName());
    }
}
