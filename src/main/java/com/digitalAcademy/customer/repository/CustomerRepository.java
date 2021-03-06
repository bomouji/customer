package com.digitalAcademy.customer.repository;

import com.digitalAcademy.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findByFirstname(String firstname);
    Customer findAllById(Long id);
}
