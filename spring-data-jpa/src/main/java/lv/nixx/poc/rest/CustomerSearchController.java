package lv.nixx.poc.rest;

import lv.nixx.poc.model.CustomerSearch;
import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.repository.advanced.CustomerCustomRepository;
import lv.nixx.poc.service.CustomerSearchUsingSpecification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CustomerSearchController {

    private final CustomerSearchUsingSpecification customerSearchUsingSpecification;
    private final CustomerCustomRepository customerCustomRepository;

    public CustomerSearchController(CustomerSearchUsingSpecification customerSearchUsingSpecification, CustomerCustomRepository customerCustomRepository) {
        this.customerSearchUsingSpecification = customerSearchUsingSpecification;
        this.customerCustomRepository = customerCustomRepository;
    }

    @PostMapping("/customer/search/using_specification")
    public Collection<Customer> searchUsingSpecification(CustomerSearch request) {
        return customerSearchUsingSpecification.search(request);
    }

    @PostMapping("/customer/search/using_custom_repository")
    public Collection<Customer> searchUsingCustomRepository(CustomerSearch request) {
        return customerCustomRepository.findCustomersUsingCustomCondition(request.getName(), request.getType());
    }

}

