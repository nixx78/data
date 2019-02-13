package lv.nixx.poc.spring.data;

import lv.nixx.poc.spring.data.repository.CustomerRepository;
import lv.nixx.poc.spring.domain.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
