package com.aishwarya.customerservice.service;

import com.aishwarya.customerservice.exception.CustomerException;
import com.aishwarya.customerservice.model.Customer;

import java.util.List;

public interface CustomerService {

    String saveCustomer(Customer customerDTO) throws CustomerException;

    String updateCustomer(Customer customerDTO, Integer id) throws CustomerException;

    Customer getSpecificCustomerData(Integer customerNumber) throws Exception;

    String deleteCustomer(Integer id);

    List<Customer> getAllCustomerData();
}
