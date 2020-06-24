package com.digitalAcademy.customer.service;

import com.digitalAcademy.customer.model.Customer;
import com.digitalAcademy.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    public List<Customer> getCustomerList(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id){
        return customerRepository.findAllById(id);
    }

    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> getCustomerByName(String name){
        return customerRepository.findByFirstname(name);
    }

    public Customer updateCustomer(Long id ,Customer customerRes){
        return customerRepository.findAllById(id) != null?
        customerRepository.save(customerRes):null;
    }

    public boolean deleteById(Long id){
        try{
            customerRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
