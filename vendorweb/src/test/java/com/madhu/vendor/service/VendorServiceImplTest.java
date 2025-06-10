package com.madhu.vendor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.madhu.vendor.entities.Vendor;
import com.madhu.vendor.repos.VendorRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("VendorServiceImpl Test")
class VendorServiceImplTest {

    private static final int VENDOR_ID = 1;
    private static final String VENDOR_NAME = "Test Vendor";

    @Mock
    private VendorRepository repository;

    private VendorServiceImpl service;

    private Vendor vendor;

    @BeforeEach
    void setUp() {
        vendor = new Vendor();
        vendor.setId(VENDOR_ID);
        vendor.setName(VENDOR_NAME);
        service = new VendorServiceImpl(repository, true);
    }

    @Test
    @DisplayName("saveVendor returns saved entity")
    void saveVendorShouldReturnSavedEntity() {
        when(repository.save(vendor)).thenReturn(vendor);

        final Vendor result = service.saveVendor(vendor);

        assertSame(vendor, result);
        verify(repository).save(vendor);
    }

    @Test
    @DisplayName("saveVendor does not persist when disabled")
    void saveVendorShouldNotPersistWhenDisabled() {
        VendorServiceImpl disabled = new VendorServiceImpl(repository, false);

        final Vendor result = disabled.saveVendor(vendor);

        assertSame(vendor, result);
        verify(repository, never()).save(vendor);
    }

    @Test
    @DisplayName("updateVendor returns updated entity")
    void updateVendorShouldReturnUpdatedEntity() {
        when(repository.save(vendor)).thenReturn(vendor);

        final Vendor result = service.updateVendor(vendor);

        assertSame(vendor, result);
        verify(repository).save(vendor);
    }

    @Test
    @DisplayName("deleteVendor removes vendor")
    void deleteVendorShouldCallRepository() {
        service.deleteVendor(vendor);

        verify(repository).delete(vendor);
    }

    @Test
    @DisplayName("getVendorById returns vendor")
    void getVendorByIdShouldReturnVendor() {
        when(repository.findById(VENDOR_ID)).thenReturn(Optional.of(vendor));

        final Vendor result = service.getVendorById(VENDOR_ID);

        assertSame(vendor, result);
        verify(repository).findById(VENDOR_ID);
    }

    @Test
    @DisplayName("getAllVendors returns vendor list")
    void getAllVendorsShouldReturnList() {
        List<Vendor> vendors = Collections.singletonList(vendor);
        when(repository.findAll()).thenReturn(vendors);

        final List<Vendor> result = service.getAllVendors();

        assertEquals(1, result.size());
        assertSame(vendor, result.get(0));
        verify(repository).findAll();
    }
}
