package com.assignment.customer_management.service;

import com.assignment.customer_management.model.Customer;
import com.assignment.customer_management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        Customer existing = customerRepository.findByNic(customer.getNic());
        if (existing != null) {
            throw new RuntimeException();
        }
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}