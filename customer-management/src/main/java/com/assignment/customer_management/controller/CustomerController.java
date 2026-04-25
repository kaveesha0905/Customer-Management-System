package com.assignment.customer_management.controller;

import com.assignment.customer_management.model.Customer;
import com.assignment.customer_management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:5174") // React වලට අවසර දීම
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    // පිටු වශයෙන් දත්ත ලබා ගැනීම (Pagination)
    @GetMapping("/paged")
    public Page<Customer> getCustomersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    // දත්ත ලක්ෂ 10ක් එකපාර හදන ක්‍රමය
    @GetMapping("/generate-bulk-data")
    public ResponseEntity<String> generateBulkData() {
        try {
            int totalRecords = 1000000;
            int batchSize = 5000;
            List<Customer> customers = new ArrayList<>();

            for (int i = 1; i <= totalRecords; i++) {
                Customer c = new Customer();
                c.setName("Customer " + i);
                c.setNic("NIC-" + String.format("%09d", i));
                c.setDob(new Date());
                customers.add(c);

                if (i % batchSize == 0) {
                    repository.saveAll(customers);
                    customers.clear();
                }
            }
            return ResponseEntity.ok("ලක්ෂ 10ක් සාර්ථකව හැදුණා!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("වැරදීමක්: " + e.getMessage());
        }
    }
}