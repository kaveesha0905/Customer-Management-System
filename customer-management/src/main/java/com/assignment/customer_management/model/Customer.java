package com.assignment.customer_management.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(unique = true, nullable = false)
    private String nic;

    // (Self-referencing relationship)
    @ManyToOne
    @JoinColumn(name = "parent_customer_id")
    private Customer parentCustomer;
}
