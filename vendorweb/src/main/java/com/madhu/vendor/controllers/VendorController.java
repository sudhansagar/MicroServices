package com.madhu.vendor.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.madhu.vendor.entities.Vendor;
import com.madhu.vendor.service.VendorService;

@Controller
public class VendorController {

	@Autowired
	VendorService service;

	@RequestMapping("/showCreate")
	public String showCreate() {
		return "createVendor";
	}

	@RequestMapping("/saveVendor")
	public String saveLocation(@ModelAttribute("vendor") Vendor vendor, ModelMap modelMap) {
		Vendor vendorSaved = service.saveVendor(vendor);
		String msg = "Vendor saved successfully with id : " + vendorSaved.getId();
		modelMap.addAttribute("msg", msg);
		return "createVendor";
	}
	
	@RequestMapping("/displayVendors")
	public String displayLocations(ModelMap vendors) {
		List<Vendor> allLocations = service.getAllVendors();
		vendors.addAttribute("vendors",allLocations);
		return "displayVendors";
	}
	
	@RequestMapping("/deleteVendor")
	public String deleteLocation(@RequestParam("vendorId") int id, ModelMap vendors) {
		Vendor location = new Vendor();
		location.setId(id);
		service.deleteVendor(location);
		List<Vendor> allVendors = service.getAllVendors();
		vendors.addAttribute("vendors",allVendors);
		return "displayVendors";
	}
	
	@RequestMapping("/showUpdate")
	public String showUpdate(@RequestParam("vendorId") int id, ModelMap location) {
		Vendor vendorObj = service.getVendorById(id);
		location.addAttribute("vendor",vendorObj);
		return "updateVendor";
	}
	
	@RequestMapping("/updateVendor")
	public String editLocation(@ModelAttribute("vendor") Vendor vendor, ModelMap vendors) {
		service.updateVendor(vendor);
		List<Vendor> allVendors = service.getAllVendors();
		vendors.addAttribute("vendors",allVendors);
		return "displayVendors";
	}

}
