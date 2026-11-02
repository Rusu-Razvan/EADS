package org.example.vehicle_routing_optimization_system.service;

import org.example.vehicle_routing_optimization_system.dto.CustomerDTO;
import org.example.vehicle_routing_optimization_system.exception.ResourceNotFoundException;
import org.example.vehicle_routing_optimization_system.model.Customer;
import org.example.vehicle_routing_optimization_system.repository.CustomerRepository;
import org.example.vehicle_routing_optimization_system.model.Coordinates;
import org.example.vehicle_routing_optimization_system.dto.CoordinatesDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // --- METODE PENTRU DTO-URI ȘI CONTROLLER ---

    public List<CustomerDTO> getAllCustomersDto() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public CustomerDTO getCustomerDtoById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clientul cu ID-ul " + id + " nu a fost găsit."));
        return convertToDTO(customer);
    }

    public CustomerDTO getCustomerDTOById(Long id) {
        return getCustomerDtoById(id);
    }

    public List<CustomerDTO> getAllCustomersDTO() {
        return getAllCustomersDto();
    }

    public CustomerDTO createCustomer(CustomerDTO dto) {
        Customer customer = convertToEntity(dto);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO dto) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clientul cu ID-ul " + id + " nu a fost găsit pentru actualizare."));

        existingCustomer.setName(dto.getName());
        existingCustomer.setEmail(dto.getEmail());
        existingCustomer.setPhone(dto.getPhone());
        existingCustomer.setAddress(dto.getAddress());
        existingCustomer.setCoordinates(dto.getCoordinates() == null ? null :
                new Coordinates(dto.getCoordinates().getX(), dto.getCoordinates().getY()));

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return convertToDTO(updatedCustomer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Clientul cu ID-ul " + id + " nu a fost găsit pentru ștergere.");
        }
        customerRepository.deleteById(id);
    }

    // --- METODE DE CONVERSIE (Helper) ---

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());

        if (customer.getCoordinates() != null) {
            CoordinatesDTO coordDto = new CoordinatesDTO();
            coordDto.setX(customer.getCoordinates().getX());
            coordDto.setY(customer.getCoordinates().getY());
            dto.setCoordinates(coordDto);
        }

        return dto;
    }

    private Customer convertToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());

        if (dto.getCoordinates() != null) {
            Coordinates coords = new Coordinates();
            coords.setX(dto.getCoordinates().getX());
            coords.setY(dto.getCoordinates().getY());
            customer.setCoordinates(coords);
        }

        return customer;
    }
}