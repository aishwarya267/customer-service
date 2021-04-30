package com.aishwarya.customerservice.service.impl;

import com.aishwarya.customerservice.constants.ResponseMessages;
import com.aishwarya.customerservice.exception.CustomerException;
import com.aishwarya.customerservice.model.Customer;
import com.aishwarya.customerservice.repository.CustomerRepository;
import com.aishwarya.customerservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public String saveCustomer(Customer customer) throws CustomerException {
        log.info("Started saving Customer Records");

        final Optional<Customer> customerInfo = customerRepository.
                findByCustomerNumber(customer.getCustomerNumber());

        if (!customerInfo.isPresent()) {
            customerRepository.save(customer);

        } else {
            throw new CustomerException(ResponseMessages.RECORD_ALREADY_PRESENT, HttpStatus.BAD_REQUEST);
        }
        return ResponseMessages.SAVE_SUCCESS;
    }

    @Override
    public String updateCustomer(Customer customer, Integer id) throws CustomerException {
        log.info("Started updating Customer details");

        final Optional<Customer> customerInfo = customerRepository.findByCustomerNumber(id);

        if (customerInfo.isPresent()) {
            customerRepository.save(customer);
            return ResponseMessages.UPDATE_SUCCESS;
        } else {
            throw new CustomerException(ResponseMessages.CUSTOMER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public Customer getSpecificCustomerData(Integer customerNumber) {
        log.info("Started fetching specific customer detail");
        final Optional<Customer> customerInfo = customerRepository.
                findByCustomerNumber(customerNumber);
        return customerInfo.orElseThrow(() -> new CustomerException(ResponseMessages.CUSTOMER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public List<Customer> getAllCustomerData() {
        log.info("Started Fetching all Customer information");
        final List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) {
            throw new CustomerException(ResponseMessages.CUSTOMER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return customerList;
    }

    @Override
    public String deleteCustomer(Integer customerNumber) {
        log.info("Started deleting specific Customer information");
        final Optional<Customer> customerInfo = customerRepository.
                findByCustomerNumber(customerNumber);
        if (customerInfo.isPresent()) {
            customerRepository.deleteByCustomerNumber(customerNumber);
            return ResponseMessages.DELETE_SUCCESS;
        } else {
            throw new CustomerException(ResponseMessages.CUSTOMER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
}
