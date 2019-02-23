package lv.nixx.poc.spring.data.repository.main;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import lv.nixx.poc.spring.data.domain.main.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	public List<Customer> findByLastName(String lastName);

	@Query("Select distinct c from Customer c left join fetch c.extension left join fetch c.type left join fetch c.adress")
	public List<Customer> findAllCustomers();

	@Query(name = "Customer.selectAllCustomersQuery")
	public List<Customer> selectAllCustomersUsingNamedQuery();
}
