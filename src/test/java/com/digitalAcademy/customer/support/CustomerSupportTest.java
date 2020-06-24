package com.digitalAcademy.customer.support;

import com.digitalAcademy.customer.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerSupportTest {
    public static List<Customer> getCustomerList(){

        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstname("Hello");
        customer.setLastname("World");
        customer.setEmail("test@test.com");
        customer.setPhoneNo("0xxxxxxxx");
        customer.setAge(10);
        customers.add(customer);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstname("David");
        customer2.setLastname("Beckham");
        customer2.setEmail("david@beckham.com");
        customer2.setPhoneNo("0zzzzzzzzz");
        customer2.setAge(40);
        customers.add(customer2);
        return customers;
    }

    public static Customer getFirstCustomer(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstname("Hello");
        customer.setLastname("World");
        customer.setEmail("test@test.com");
        customer.setPhoneNo("0xxxxxxxx");
        customer.setAge(10);
        return customer;
    }

    public static List<Customer> getSameNameCustomer(){
        List<Customer> customers = new ArrayList<>();

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstname("David");
        customer2.setLastname("Beckham");
        customer2.setEmail("david@beckham.com");
        customer2.setPhoneNo("0zzzzzzzzz");
        customer2.setAge(40);
        customers.add(customer2);

        Customer customer3 = new Customer();
        customer3.setId(3L);
        customer3.setFirstname("David");
        customer3.setLastname("Copperfield");
        customer3.setEmail("david@copperfield.com");
        customer3.setPhoneNo("0zzzzzzzzz");
        customer3.setAge(40);
        customers.add(customer3);

        return customers;
    }

    public static Customer getNewCustomer(){
        Customer customer = new Customer();
        customer.setFirstname("Firstname");
        customer.setLastname("Lastname");
        customer.setEmail("firstname@lastname.com");
        customer.setPhoneNo("XXXXXXXXXX");
        customer.setAge(22);
        return customer;
    }

    public static Customer getNewCustomerResponse(){
        Customer customerReturn = new Customer();
        customerReturn.setId(1L);
        customerReturn.setFirstname("Firstname");
        customerReturn.setLastname("Lastname");
        customerReturn.setEmail("firstname@lastname.com");
        customerReturn.setPhoneNo("XXXXXXXXXX");
        customerReturn.setAge(22);
        return customerReturn;
    }

    public static Customer getOldCustomer(){
        Customer oldCustomer = new Customer();
        oldCustomer.setId(1L);
        oldCustomer.setFirstname("Old");
        oldCustomer.setLastname("Old");
        oldCustomer.setEmail("old@old.com");
        oldCustomer.setPhoneNo("XXXXXXXXXX");
        oldCustomer.setAge(11);
        return oldCustomer;
    }

}
