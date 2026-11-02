package org.example.vehicle_routing_optimization_system.service;

import org.example.vehicle_routing_optimization_system.model.Customer;
import org.example.vehicle_routing_optimization_system.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    // 1. Pentru Derived Query (findByEmail)
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    // 2. Pentru JPQL Query (searchByAddressKeyword)
    public List<Customer> searchCustomersByAddress(String keyword) {
        return customerRepository.searchByAddressKeyword(keyword);
    }

    // 3. Pentru Modifying Query (updateCustomerPhone)
    public int updatePhone(Long id, String newPhone) {
        return customerRepository.updateCustomerPhone(id, newPhone);
    }
}