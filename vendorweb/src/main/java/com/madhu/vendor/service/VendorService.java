package com.madhu.vendor.service;

import java.util.List;

import com.madhu.vendor.entities.Vendor;

public interface VendorService {

	Vendor saveVendor(Vendor Vendor);

	Vendor updateVendor(Vendor Vendor);

	void deleteVendor(Vendor Vendor);

	Vendor getVendorById(int id);

	List<Vendor> getAllVendors();
}
