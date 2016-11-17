package lv.nixx.poc.spring.data;

import lv.nixx.poc.spring.data.domain.Customer;
import lv.nixx.poc.spring.data.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerDAO {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer save(Customer customer){
		return customerRepository.save(customer);
	}

	public Iterable<Customer> findAll(){
		return customerRepository.findAll();
	}

}
