package com.metasol.entity;

import jakarta.persistence.PrePersist;

public class CustomerEntityListener {
    @PrePersist
    public void onCreate(CustomerEntity customer) {
        if (customer.isActive()) {
            customer.setActive(true);
        }
    }
}
