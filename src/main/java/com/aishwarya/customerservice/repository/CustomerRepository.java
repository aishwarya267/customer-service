package com.aishwarya.customerservice.repository;

import com.aishwarya.customerservice.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Integer> {
    Optional<Customer> findByCustomerNumber(Integer customerNumber);

    Customer deleteByCustomerNumber(Integer customerNumber);

}
