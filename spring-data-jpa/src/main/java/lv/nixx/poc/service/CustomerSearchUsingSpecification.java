package lv.nixx.poc.service;

import lv.nixx.poc.model.CustomerSearch;
import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomerSearchUsingSpecification {

    private final CustomerRepository customerRepository;

    public CustomerSearchUsingSpecification(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Collection<Customer> search(CustomerSearch request) {
        return null;
    }
}
