package com.madhu.vendor.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madhu.vendor.entities.Vendor;
import com.madhu.vendor.service.VendorService;

@RestController
@RequestMapping("/vendors")
public class vendorRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(vendorRestController.class);
	
	@Autowired
	VendorService service;
	
	@GetMapping
	public List<Vendor> showVendors() {
		LOGGER.info("Inside showVendors");
		return service.getAllVendors();
	}
	
	@GetMapping("/{id}")
	public Vendor findVendor(@PathVariable("id") Integer id) {
		LOGGER.info("Inside findVendor() for id: " + id);
		return service.getVendorById(id);
	}
	

	@PostMapping
	public Vendor createVendor(@RequestBody Vendor vendor) {
		LOGGER.info("Inside createVendor");
		return service.saveVendor(vendor);
	}
	
	@PutMapping
	public Vendor updateVendor(@RequestBody Vendor vendor) {
		LOGGER.info("Inside updateVendor");
		return service.saveVendor(vendor);
	}
	
	@DeleteMapping("/{id}")
	public void deleteVendor(@PathVariable("id") int id) {
		LOGGER.info("Inside deleteVendor");
		Vendor vendor = new Vendor();
		vendor.setId(id);
		service.deleteVendor(vendor);
	}

}
