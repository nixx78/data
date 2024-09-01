package lv.nixx.poc.rest;

import lv.nixx.poc.model.CustomerSearch;
import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.service.CustomerSearchUsingSpecification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CustomerSearchController {

    private final CustomerSearchUsingSpecification customerSearchUsingSpecification;

    public CustomerSearchController(CustomerSearchUsingSpecification customerSearchUsingSpecification) {
        this.customerSearchUsingSpecification = customerSearchUsingSpecification;
    }

    @PostMapping("/customer/search")
    public Collection<Customer> searchCustomer(CustomerSearch request) {
     return customerSearchUsingSpecification.search(request);
    }
}

