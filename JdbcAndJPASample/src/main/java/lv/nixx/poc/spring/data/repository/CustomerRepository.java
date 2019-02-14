package lv.nixx.poc.spring.data.repository;

import java.util.List;

import lv.nixx.poc.spring.data.domain.Customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	public List<Customer> findByLastName(String lastName);
	
	@Query("Select distinct c from Customer c left join fetch c.extension left join fetch c.type left join fetch c.adress")
	public List<Customer> findAllCustomers();
	
	@Query(name="Customer.selectAllCustomersQuery")
	public List<Customer> selectAllCustomersUsingNamedQuery();
}
