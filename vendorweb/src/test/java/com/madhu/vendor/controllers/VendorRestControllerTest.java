package com.madhu.vendor.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.madhu.vendor.entities.Vendor;
import com.madhu.vendor.service.VendorService;

@WebMvcTest(vendorRestController.class)
@ExtendWith(SpringExtension.class)
@DisplayName("vendorRestController Test")
class VendorRestControllerTest {

    private static final int VENDOR_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendorService service;

    @Test
    @DisplayName("showVendors returns vendor list")
    void showVendorsReturnsList() throws Exception {
        when(service.getAllVendors()).thenReturn(Collections.singletonList(new Vendor()));

        mockMvc.perform(get("/vendors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    @DisplayName("findVendor returns vendor")
    void findVendorReturnsVendor() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setId(VENDOR_ID);
        when(service.getVendorById(VENDOR_ID)).thenReturn(vendor);

        mockMvc.perform(get("/vendors/{id}", VENDOR_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VENDOR_ID));
    }

    @Test
    @DisplayName("createVendor saves vendor")
    void createVendorSavesVendor() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setId(VENDOR_ID);
        when(service.saveVendor(any(Vendor.class))).thenReturn(vendor);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/vendors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vendor)))
                .andExpect(status().isOk());

        verify(service).saveVendor(any(Vendor.class));
    }

    @Test
    @DisplayName("updateVendor updates vendor")
    void updateVendorUpdatesVendor() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setId(VENDOR_ID);
        when(service.saveVendor(any(Vendor.class))).thenReturn(vendor);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(put("/vendors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vendor)))
                .andExpect(status().isOk());

        verify(service).saveVendor(any(Vendor.class));
    }

    @Test
    @DisplayName("deleteVendor removes vendor")
    void deleteVendorRemovesVendor() throws Exception {
        mockMvc.perform(delete("/vendors/{id}", VENDOR_ID))
                .andExpect(status().isOk());

        verify(service).deleteVendor(any(Vendor.class));
    }
}
