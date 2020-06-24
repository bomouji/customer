package com.digitalAcademy.customer.service;

import com.digitalAcademy.customer.model.Customer;
import com.digitalAcademy.customer.repository.CustomerRepository;
import com.digitalAcademy.customer.support.CustomerSupportTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @DisplayName("Test get all customer should return list")
    @Test
    void testGetAllCustomer(){
        List<Customer> customers = CustomerSupportTest.getCustomerList();

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> customerRes = customerService.getCustomerList();


        assertEquals(1,customerRes.get(0).getId().intValue());
        assertEquals("Hello",customerRes.get(0).getFirstname());
        assertEquals("World",customerRes.get(0).getLastname());
        assertEquals("test@test.com",customerRes.get(0).getEmail());
        assertEquals("0xxxxxxxx",customerRes.get(0).getPhoneNo());
        assertEquals(10,customerRes.get(0).getAge().intValue());

        assertEquals(2,customerRes.get(1).getId().intValue());
        assertEquals("David",customerRes.get(1).getFirstname());
        assertEquals("Beckham",customerRes.get(1).getLastname());
        assertEquals("david@beckham.com",customerRes.get(1).getEmail());
        assertEquals("0zzzzzzzzz",customerRes.get(1).getPhoneNo());
        assertEquals(40,customerRes.get(1).getAge().intValue());
    }

    @DisplayName("Test get  customer by id should return customer")
    @Test
    void testGetCustomerById(){

        Customer customer = CustomerSupportTest.getFirstCustomer();

        when(customerRepository.findAllById(1L)).thenReturn(customer);

        Customer customerRes = customerService.getCustomerById(1L);

        assertEquals(1,customerRes.getId().intValue());
        assertEquals("Hello",customerRes.getFirstname());
        assertEquals("World",customerRes.getLastname());
        assertEquals("test@test.com",customerRes.getEmail());
        assertEquals("0xxxxxxxx",customerRes.getPhoneNo());
        assertEquals(10,customerRes.getAge().intValue());

    }

    @DisplayName("Test get customer by name should return customer")
    @Test
    void testGetCustomerByName(){
        List<Customer> customers = CustomerSupportTest.getSameNameCustomer();


        when(customerRepository.findByFirstname("David")).thenReturn(customers);

        List<Customer> customerRes = customerService.getCustomerByName("David");

        assertEquals(2,customerRes.get(0).getId().intValue());
        assertEquals("David",customerRes.get(0).getFirstname());
        assertEquals("Beckham",customerRes.get(0).getLastname());
        assertEquals("david@beckham.com",customerRes.get(0).getEmail());
        assertEquals("0zzzzzzzzz",customerRes.get(0).getPhoneNo());
        assertEquals(40,customerRes.get(0).getAge().intValue());

        assertEquals(3,customerRes.get(1).getId().intValue());
        assertEquals("David",customerRes.get(1).getFirstname());
        assertEquals("Copperfield",customerRes.get(1).getLastname());
        assertEquals("david@copperfield.com",customerRes.get(1).getEmail());
        assertEquals("0zzzzzzzzz",customerRes.get(1).getPhoneNo());
        assertEquals(40,customerRes.get(1).getAge().intValue());
    }

    @DisplayName("Test create customer should return new customer")
    @Test
    void testCreateCustomer(){

        Customer customer = CustomerSupportTest.getNewCustomer();

        Customer customerReturn = CustomerSupportTest.getNewCustomerResponse();

        when(customerRepository.save(customer)).thenReturn(customerReturn);
        Customer customerreq = customerService.createCustomer(customer);

        assertEquals(1,customerreq.getId().intValue());
        assertEquals("Firstname",customerreq.getFirstname());
        assertEquals("Lastname",customerreq.getLastname());
        assertEquals("firstname@lastname.com",customerreq.getEmail());
        assertEquals("XXXXXXXXXX",customerreq.getPhoneNo());
        assertEquals(22,customerreq.getAge().intValue());

    }

    @DisplayName("Test update customer should return true")
    @Test
    void testUpdateCustomer(){
        Customer customer = CustomerSupportTest.getNewCustomerResponse();


        Customer oldCustomer = CustomerSupportTest.getOldCustomer();

        when(customerRepository.findAllById(1L)).thenReturn(oldCustomer);
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer resq = customerService.updateCustomer(1L,customer);

        assertEquals(1,resq.getId().intValue());
        assertEquals("Firstname",resq.getFirstname());
        assertEquals("Lastname",resq.getLastname());
        assertEquals("firstname@lastname.com",resq.getEmail());
        assertEquals("XXXXXXXXXX",resq.getPhoneNo());
        assertEquals(22,resq.getAge().intValue());
    }

    @DisplayName("Test update customer should return fail")
    @Test
    void testUpdateCustomerFail(){
        Customer customer = CustomerSupportTest.getNewCustomerResponse();

        when(customerRepository.findAllById(1L)).thenReturn(null);

        Customer resq = customerService.updateCustomer(4L,customer);

        assertEquals(null,resq);

    }

    @DisplayName("Test Delete customer should return true")
    @Test
    void testDeleteCustomer(){
        doNothing().when(customerRepository).deleteById(1L);
        boolean resq = customerService.deleteById(1L);
        assertEquals(true,resq);
        assertTrue(resq);
        assertTrue(customerService.deleteById(1L));
    }

    @DisplayName("Test Delete customer should return false")
    @Test
    void testDeleteCustomerFail(){
        doThrow(Exception.class)
                .when(customerRepository).deleteById(1L);
        boolean resp = customerService.deleteById(1l);
        assertEquals(false,resp);
        assertFalse(resp);
        assertFalse(customerService.deleteById(1L));

    }


}
