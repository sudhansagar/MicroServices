package com.madhu.customerdata.repos;

import org.springframework.data.repository.CrudRepository;

import com.madhu.customerdata.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
