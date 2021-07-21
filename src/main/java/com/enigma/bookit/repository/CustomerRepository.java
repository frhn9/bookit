package com.enigma.bookit.repository;

import com.enigma.bookit.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
