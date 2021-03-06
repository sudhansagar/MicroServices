package com.madhu.location.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.madhu.location.entities.Location;
import com.madhu.location.service.LocationService;

@Controller
public class LocationController {
	
	private static final Logger LOG = LoggerFactory.getLogger(LocationController.class);

	@Autowired
	LocationService service;

	@RequestMapping("/showCreate")
	public String showCreate() {
		LOG.info("Creating Location");
		return "createLocation";
	}

	@RequestMapping("/saveLoc")
	public String saveLocation(@ModelAttribute("location") Location location, ModelMap modelMap) {
		LOG.info("Saving Location");
		Location locationSaved = service.saveLocation(location);
		String msg = "Location Saved successfully with id : " + locationSaved.getId();
		modelMap.addAttribute("msg", msg);
		return "createLocation";
	}
	
	@RequestMapping("/displayLocations")
	public String displayLocations(ModelMap locations) {
		LOG.info("Displaying Locations");
		List<Location> allLocations = service.getAllLocations();
		locations.addAttribute("locations",allLocations);
		return "displayLocations";
	}
	
	@RequestMapping("/deleteLoc")
	public String deleteLocation(@RequestParam("locationId") int id, ModelMap locations) {
		LOG.info("Deleting Location");
		Location location = new Location();
		location.setId(id);
		service.deleteLocation(location);
		List<Location> allLocations = service.getAllLocations();
		locations.addAttribute("locations",allLocations);
		return "displayLocations";
	}
	
	@RequestMapping("/showUpdate")
	public String showUpdate(@RequestParam("locationId") int id, ModelMap location) {
		LOG.info("Updating Location");
		Location locationObj = service.getLocationById(id);
		location.addAttribute("location",locationObj);
		return "updateLocation";
	}
	
	@RequestMapping("/updateLoc")
	public String editLocation(@ModelAttribute("location") Location location, ModelMap locations) {
		service.updateLocation(location);
		List<Location> allLocations = service.getAllLocations();
		locations.addAttribute("locations",allLocations);
		return "displayLocations";
	}

}
