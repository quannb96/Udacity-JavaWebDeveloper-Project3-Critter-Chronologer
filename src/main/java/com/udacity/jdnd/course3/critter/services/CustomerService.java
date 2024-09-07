package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CustomerService {
    
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    public Customer getCustomerByPetId(Long petId) {
        Customer customer = petRepository.getOne(petId).getCustomer();
        return customer;
    }

    public Customer getSelectedCustomer(Customer customer, List<Long> petIds) {
        List<Pet> customerPets = new ArrayList<>();
        if (petIds != null && !petIds.isEmpty()) {
            petIds.forEach(petId -> customerPets.add(petRepository.getOne(petId)));
        }
        customer.setPets(customerPets);
        return customer;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
