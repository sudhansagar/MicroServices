package com.madhu.customerdata;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.madhu.customerdata.entities.Customer;
import com.madhu.customerdata.repos.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerdataApplicationTests {

	@Autowired
	private CustomerRepository customerRepository;	
	
	@Test
	public void contextLoads() {
		System.out.println("Hello");
	}
	
	@Test
	public void addCustomer() {
		Customer cust = new Customer();
		cust.setName("madhu");
		cust.setEmail("madhu1@gmail.com");
		customerRepository.save(cust);
	}
	
	@Test
	public void findCustomer() {
		Customer cust = customerRepository.findById(1l).get();
		System.out.println(cust);
	}
	

	@Test
	public void update() {
		Customer cust = customerRepository.findById(2l).get();
		cust.setEmail("madhu@gmail.com");
		customerRepository.save(cust);
	}
	
	@Test
	public void deleteCustomer() {
		Customer cust = customerRepository.findById(3l).get();
		customerRepository.delete(cust);
	}
}
