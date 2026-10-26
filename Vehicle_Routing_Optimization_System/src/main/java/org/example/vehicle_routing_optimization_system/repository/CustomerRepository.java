package org.example.vehicle_routing_optimization_system.repository;

import org.example.vehicle_routing_optimization_system.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // 1. Derived Query Method (generat automat după nume)
    Optional<Customer> findByEmail(String email);

    // 2. Explicitly defined JPQL Query
    @Query("SELECT c FROM Customer c WHERE c.address LIKE %:keyword%")
    List<Customer> searchByAddressKeyword(@Param("keyword") String keyword);

    // 3. Transactional Modifying Query
    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.phone = :phone WHERE c.id = :id")
    int updateCustomerPhone(@Param("id") Long id, @Param("phone") String phone);
}