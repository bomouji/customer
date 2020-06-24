package com.digitalAcademy.customer.controller;

import com.digitalAcademy.customer.contoller.CustomerController;
import com.digitalAcademy.customer.model.Customer;
import com.digitalAcademy.customer.repository.CustomerRepository;
import com.digitalAcademy.customer.service.CustomerService;
import com.digitalAcademy.customer.support.CustomerSupportTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.xml.internal.ws.developer.Serialization;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mvc;

    private static final String urlCustomer = "/customer/";

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService);
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @DisplayName("Test Customer should return Customer List")
    @Test
    void testGetCustomerList() throws Exception{
        when(customerService.getCustomerList())
                .thenReturn(CustomerSupportTest.getCustomerList());

        MvcResult mvcResult= mvc.perform(get(urlCustomer + "list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jsonArray= new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals("1",jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("Hello",jsonArray.getJSONObject(0).get("firstname").toString());
        assertEquals("World",jsonArray.getJSONObject(0).get("lastname").toString());
        assertEquals("test@test.com",jsonArray.getJSONObject(0).get("email").toString());
        assertEquals("0xxxxxxxx",jsonArray.getJSONObject(0).get("phoneNo").toString());
        assertEquals(10,jsonArray.getJSONObject(0).get("age"));

        assertEquals("2",jsonArray.getJSONObject(1).get("id").toString());
        assertEquals("David",jsonArray.getJSONObject(1).get("firstname").toString());
        assertEquals("Beckham",jsonArray.getJSONObject(1).get("lastname").toString());
        assertEquals("david@beckham.com",jsonArray.getJSONObject(1).get("email").toString());
        assertEquals("0zzzzzzzzz",jsonArray.getJSONObject(1).get("phoneNo").toString());
        assertEquals(40,jsonArray.getJSONObject(1).get("age"));
    }

    @DisplayName("Test get customer by id should return customer")
    @Test
    void testGetCustomerById() throws Exception{
        when(customerService.getCustomerById(1L)).thenReturn(CustomerSupportTest.getFirstCustomer());

        MvcResult mvcResult= mvc.perform(get(urlCustomer +""+ 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject jsonObject= new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("1",jsonObject.get("id").toString());
        assertEquals("Hello",jsonObject.get("firstname").toString());
        assertEquals("World",jsonObject.get("lastname").toString());
        assertEquals("test@test.com",jsonObject.get("email").toString());
        assertEquals("0xxxxxxxx",jsonObject.get("phoneNo").toString());
        assertEquals(10,jsonObject.get("age"));

    }

    @DisplayName("Test get customer by id should return return null")
    @Test
    void testGetCustomerByIdNotFound() throws Exception{
        MvcResult mvcResult = mvc.perform(get(urlCustomer+ ""+ 10L))
                .andExpect(status().isNotFound())
                .andReturn();
    }


    @DisplayName("Test get customer by name should return success.")
    @Test
    void testGetCustomerByName() throws Exception{
        when(customerService.getCustomerByName("David")).thenReturn(CustomerSupportTest.getSameNameCustomer());

        MvcResult mvcResult= mvc.perform(get(urlCustomer +""+"?name=David"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jsonArray= new JSONArray(mvcResult.getResponse().getContentAsString());

        assertEquals("2",jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("David",jsonArray.getJSONObject(0).get("firstname").toString());
        assertEquals("Beckham",jsonArray.getJSONObject(0).get("lastname").toString());
        assertEquals("david@beckham.com",jsonArray.getJSONObject(0).get("email").toString());
        assertEquals("0zzzzzzzzz",jsonArray.getJSONObject(0).get("phoneNo").toString());
        assertEquals(40,jsonArray.getJSONObject(0).get("age"));

        assertEquals("3",jsonArray.getJSONObject(1).get("id").toString());
        assertEquals("David",jsonArray.getJSONObject(1).get("firstname").toString());
        assertEquals("Copperfield",jsonArray.getJSONObject(1).get("lastname").toString());
        assertEquals("david@copperfield.com",jsonArray.getJSONObject(1).get("email").toString());
        assertEquals("0zzzzzzzzz",jsonArray.getJSONObject(1).get("phoneNo").toString());
        assertEquals(40,jsonArray.getJSONObject(1).get("age"));
    }

    @DisplayName("Test get customer by name should return not found.")
    @Test
    void testGetCustomerByNameNotFound() throws Exception{

        MvcResult mvcResult = mvc.perform(get(urlCustomer +""+"?name=Teerayuth"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @DisplayName("Test create customer should return success")
    @Test
    void testCreateCustomer() throws Exception{

        Customer customer = CustomerSupportTest.getNewCustomer();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requireJson = ow.writeValueAsString(customer);

        when(customerService.createCustomer(customer))
                .thenReturn(CustomerSupportTest.getNewCustomerResponse());

        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requireJson))
                .andExpect(status().isCreated())
                .andReturn();

        JSONObject jsonObject= new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("1",jsonObject.get("id").toString());
        assertEquals("Firstname",jsonObject.get("firstname").toString());
        assertEquals("Lastname",jsonObject.get("lastname").toString());
        assertEquals("firstname@lastname.com",jsonObject.get("email").toString());
        assertEquals("XXXXXXXXXX",jsonObject.get("phoneNo").toString());
        assertEquals(22,jsonObject.get("age"));
    }

    @DisplayName("Test create customer with first name is empty.")
    @Test
    void testCreateCustomerWithNameIdEmpty() throws Exception{
        Customer customer = CustomerSupportTest.getNewCustomer();
        customer.setFirstname("");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requireJson = ow.writeValueAsString(customer);

        when(customerService.createCustomer(customer))
                .thenReturn(CustomerSupportTest.getNewCustomerResponse());

        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requireJson))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<?> com.digitalAcademy.customer.contoller.CustomerController.createCustomer(com.digitalAcademy.customer.model.Customer): [Field error in object 'customer' on field 'firstname': rejected value []; codes [Size.customer.firstname,Size.firstname,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [customer.firstname,firstname]; arguments []; default message [firstname],100,1]; default message [Please type firstname]] "
                ,mvcResult.getResolvedException().getMessage());
    }

    @DisplayName("Test update customer should return success")
    @Test
    void testUpdateCustomer() throws Exception{
        Customer customer = CustomerSupportTest.getOldCustomer();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requireJson = ow.writeValueAsString(customer);

        when(customerService.updateCustomer(1L,customer))
                .thenReturn(CustomerSupportTest.getNewCustomerResponse());

        MvcResult mvcResult = mvc.perform(put(urlCustomer+ "" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requireJson))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonObject= new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("1",jsonObject.get("id").toString());
        assertEquals("Firstname",jsonObject.get("firstname").toString());
        assertEquals("Lastname",jsonObject.get("lastname").toString());
        assertEquals("firstname@lastname.com",jsonObject.get("email").toString());
        assertEquals("XXXXXXXXXX",jsonObject.get("phoneNo").toString());
        assertEquals(22,jsonObject.get("age"));

    }

    @DisplayName("Test update customer should return id not found")
    @Test
    void testUpdateCustomerNotFound() throws Exception{
        Customer customer = CustomerSupportTest.getOldCustomer();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customer);

        when(customerService.updateCustomer(2L,customer))
                .thenReturn(null);

        MvcResult mvcResult = mvc.perform(put(urlCustomer+ "" + 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotFound())
                .andReturn();

//        verify(customerService,times(1)).updateCustomer(2L,customer);
    }

    @DisplayName("Test delete customer should return true")
    @Test
    void testDeleteCustomer() throws Exception{
        when(customerService.deleteById(1L))
                .thenReturn(true);

        MvcResult mvcResult = mvc.perform(delete(urlCustomer+ "" + 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        verify(customerService,times(1)).deleteById(1L);

    }

    @DisplayName("Test delete customer wrong id should return false")
    @Test
    void testDeleteCustomerNotFound() throws Exception{
        when(customerService.deleteById(4L))
                .thenReturn(false);

        MvcResult mvcResult = mvc.perform(delete(urlCustomer+ "" + 4L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        verify(customerService,times(1)).deleteById(4L);

    }
}
