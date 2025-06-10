package com.madhu.vendor.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.madhu.vendor.entities.Vendor;
import com.madhu.vendor.repos.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {


       private static final Logger LOGGER = LoggerFactory.getLogger(VendorServiceImpl.class);

       private final VendorRepository repository;
       private final boolean saveEnabled;

       @Autowired
       public VendorServiceImpl(VendorRepository repository,
                       @Value("${vendor.save.enabled:true}") boolean saveEnabled) {
               this.repository = repository;
               this.saveEnabled = saveEnabled;

	@Override
       public Vendor saveVendor(Vendor vendor) {
               if (!saveEnabled) {
                       LOGGER.warn("Save vendor feature disabled");
                       return vendor;
               }
               return repository.save(vendor);
       }

	@Override
	public Vendor updateVendor(Vendor vendor) {
		return repository.save(vendor);
	}

	@Override
	public void deleteVendor(Vendor vendor) {
		repository.delete(vendor);
	}

	@Override
       public Vendor getVendorById(int id) {
               return repository.findById(id).orElse(null);
       }

	@Override
       public List<Vendor> getAllVendors() {
               return repository.findAll();
       }

}
