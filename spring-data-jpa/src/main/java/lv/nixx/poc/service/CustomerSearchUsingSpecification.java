package lv.nixx.poc.service;

import lv.nixx.poc.model.CustomerSearch;
import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.repository.CustomerRepository;
import lv.nixx.poc.repository.advanced.CustomerSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.function.Function;

@Service
public class CustomerSearchUsingSpecification {

    private final CustomerRepository customerRepository;

    public CustomerSearchUsingSpecification(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Collection<Customer> search(CustomerSearch request) {

        Specification<Customer> specification = new SpecBuilder()
                .addCondition(request.getName(), CustomerSpecification::nameLike)
                .addCondition(request.getType(), CustomerSpecification::typeEquals)
                .build();

        return customerRepository.findAll(specification);
    }

    static class SpecBuilder {
        Specification<Customer> spec = Specification.where(null);

        private SpecBuilder addCondition(String value, Function<String, Specification<Customer>> f) {
            if (StringUtils.isNotBlank(value)) {
                this.spec = spec.and(f.apply(value));
            }
            return this;
        }

        private Specification<Customer> build() {
            return this.spec;
        }
    }

}
