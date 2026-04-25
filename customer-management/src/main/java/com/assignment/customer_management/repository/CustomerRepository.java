package com.assignment.customer_management.repository;

import com.assignment.customer_management.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // NIC eken customer kenek hoyanna meka ona wenawa
    Customer findByNic(String nic);
}