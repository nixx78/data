package lv.nixx.poc.spring.data;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
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

public class NamedQuerySample {

	@Test
	public void executeQueries() {
		
		final EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

		EntityManager entityManager = factory.createEntityManager();
		try {
		    final EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();

		    CustomerType student = new CustomerType("student", "Customer is Student");
		    CustomerType vip = new CustomerType("vip", "Customer is VIP");
			entityManager.persist(student);
			entityManager.persist(vip);
			
			Arrays.asList(
					new Customer("Jack", "Bauer", student),
					new Customer("Nikolas", "Cage", vip),
					new Customer("Piter", "First", null),
					new Customer("John", "Rembo", student),
					new Customer("John", "Down", student)
			).forEach(entityManager::persist);
			
			transaction.commit();

			TypedQuery<CustomerWithType> q = entityManager.createNamedQuery("customersWithType", CustomerWithType.class);
			
			List<CustomerWithType> resultList = q.getResultList();
			resultList.forEach(System.out::println);
			
			assertEquals(4, resultList.size());
			
			
			System.out.println("=======================");
			TypedQuery<CustomerWithType> q1 = entityManager.createNamedQuery("customerByFirstName", CustomerWithType.class);
			q1.setParameter("firstname", "John");	// Named parameter may be not supported
			
			resultList = q1.getResultList();
			
			resultList.forEach(System.out::println);
			assertEquals(2, resultList.size());
			
		} finally {
			entityManager.close();
		}

	}

}
