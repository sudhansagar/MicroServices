package com.madhu.vendor.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.madhu.vendor.entities.Vendor;
import com.madhu.vendor.service.VendorService;

@WebMvcTest(VendorController.class)
@ExtendWith(SpringExtension.class)
@DisplayName("VendorController Test")
class VendorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendorService service;

    @Test
    @DisplayName("saveVendor returns view")
    void saveVendorReturnsView() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setId(1);
        when(service.saveVendor(any(Vendor.class))).thenReturn(vendor);

        mockMvc.perform(post("/saveVendor"))
                .andExpect(status().isOk())
                .andExpect(view().name("createVendor"))
                .andExpect(model().attributeExists("msg"));

        verify(service).saveVendor(any(Vendor.class));
    }

    @Test
    @DisplayName("displayVendors shows vendors")
    void displayVendorsShowsVendors() throws Exception {
        when(service.getAllVendors()).thenReturn(Collections.singletonList(new Vendor()));

        mockMvc.perform(post("/displayVendors"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayVendors"))
                .andExpect(model().attributeExists("vendors"));
    }
}
