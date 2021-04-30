package com.aishwarya.customerservice.controller;

import com.aishwarya.customerservice.exception.CustomerException;
import com.aishwarya.customerservice.model.Customer;
import com.aishwarya.customerservice.service.CustomerService;
import com.aishwarya.customerservice.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //API to save customer data
    @PostMapping(value = "/customers")
    public String saveCustomer(@RequestBody Customer customer) throws CustomerException {

        return customerService.saveCustomer(customer);
    }

    //API to update customer data
    @PutMapping(value = "/customers/{customerNumber}")
    public String updateCustomer(@RequestBody Customer customer, @PathVariable Integer customerNumber) throws CustomerException {
        return customerService.updateCustomer(customer, customerNumber);
    }

    //API to view all customers
    @GetMapping(value = "/customers")
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomerData();
    }

    //API to view a specific customer
    @GetMapping(value = "/Customers")
    public Customer getSpecificCustomerData(@RequestParam Integer customerNumber) throws Exception {
        return customerService.getSpecificCustomerData(customerNumber);
    }

    //API to delete a customer record based on customer number
    @DeleteMapping(value = "/customers")
    public String deleteCustomer(@RequestParam Integer customerNumber) {
        return customerService.deleteCustomer(customerNumber);
    }

}
