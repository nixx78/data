package lv.nixx.poc.spring.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Test;

import lv.nixx.poc.spring.data.domain.Customer;
import lv.nixx.poc.spring.data.domain.CustomerType;
import lv.nixx.poc.spring.data.domain.CustomerWithType;

public class CustomerWithTypeTest {

	@Test
	public void getAndMapCustomerWithTypes() {
		final EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

		EntityManager entityManager = factory.createEntityManager();
		
	    final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

	    CustomerType student = new CustomerType("student", "Customer is Student");
	    CustomerType vip = new CustomerType("vip", "Customer is VIP");
		entityManager.merge(student);
		entityManager.merge(vip);

		Customer c1 = new Customer("Jack", "Bauer", student);
		Customer c2 = new Customer("Nikolas", "Cage", vip);
		Customer c3 = new Customer("Piter", "First", null);
		Customer c4 = new Customer("John", "Rembo", student);
		
		entityManager.merge(c1);
		entityManager.merge(c2);
		entityManager.merge(c3);
		entityManager.merge(c4);
		
		transaction.commit();

		TypedQuery<CustomerWithType> q = entityManager.createNamedQuery("customersWithType", CustomerWithType.class);
		List<CustomerWithType> resultList = q.getResultList();
		resultList.forEach(System.out::println);

		entityManager.close();
	}

}
