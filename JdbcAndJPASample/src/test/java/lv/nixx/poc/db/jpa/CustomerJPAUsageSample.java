package lv.nixx.poc.db.jpa;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.*;
import javax.persistence.criteria.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lv.nixx.poc.db.domain.*;

public class CustomerJPAUsageSample {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

	@Before
	public void init() {
		clearDatabase(); 
		createInitialCustomers();
	}

	private void clearDatabase() {
		final EntityManager em = factory.createEntityManager();
		final EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.createQuery("DELETE FROM Customer").executeUpdate();
			em.createQuery("DELETE FROM CustomerType").executeUpdate();
			em.createQuery("DELETE FROM CustomerExtension").executeUpdate();
			transaction.commit();
			
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
			
			transaction.rollback();
		} finally {
			Optional.ofNullable(em).ifPresent(t-> t.close());
		}
	}

	@After
	public void destroy() {
		factory.close();
	}

	@Test
	public void testShouldPersistCustomer() {
		final EntityManager entityManager = factory.createEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Customer customer = new Customer("name1", "surname1", null);
		entityManager.persist(customer);

		assertNotNull(customer.getId()); // проверяем, что ID сгенерировано для Customer
		transaction.commit();
		entityManager.close();
	}

	@Test(expected = PersistenceException.class)
	public void testShouldTryToSaveCustomerWithNullNameAndGetException() {
		final EntityManager entityManager = factory.createEntityManager();

		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(new Customer(null, null, null));
		entityManager.close();
	}

	@Test
	public void testTestSaveCustomersAndRetrieveUsingCriteria() {
		final EntityManager entityManager = factory.createEntityManager();

		TypedQuery<Customer> query = entityManager.createQuery("SELECT e FROM Customer e", Customer.class);
		List<Customer> resultList = query.getResultList();

		assertEquals("Customer count", 4, resultList.size());

		for (Customer c : resultList) {
			System.out.println(c);
		}
		entityManager.close();
	}

	@Test
	public void testShouldFindCustomer() {
		final EntityManager entityManager = factory.createEntityManager();

		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		Customer c1 = new Customer("Jack", "Bauer", null);
		Customer c2 = new Customer("Nikolas", "Cage", null);

		c1 = entityManager.merge(c1);
		c2 = entityManager.merge(c2);

		transaction.commit();

		Long c1Id = c1.getId();
		Long c2Id = c2.getId();

		Customer foundC1 = entityManager.find(Customer.class, c1Id);
		Customer foundC2 = entityManager.find(Customer.class, c2Id);

		assertEquals(c1.getId(), foundC1.getId());
		assertEquals(c2.getId(), foundC2.getId());

		entityManager.close();
	}

	@Test
	public void testShouldShowHowWorkWithQueries() {
		final EntityManager entityManager = factory.createEntityManager();

		// Мы можем вызвать именованый запрос и даже передать в него параметры
		TypedQuery<Customer> query = entityManager.createNamedQuery("Customer.findCustomerByLastName", Customer.class);
		query.setParameter("lastName", "Rembo");
		List<Customer> resultList = query.getResultList();
		Customer singleResult = query.getSingleResult();
		assertNotNull(singleResult);

		System.out.println(resultList.get(0));

		// Запрос мы можем написать простым текстом
		query = entityManager.createQuery("Select distinct c from Customer c " + "left join fetch c.extension "
				+ "left join fetch c.type " + "left join fetch c.adress", Customer.class);

		resultList = query.getResultList();
		System.out.println("Customer records count: " + resultList.size());
		assertEquals("Customer count", 4, resultList.size());

		for (Customer m : resultList) {
			System.out.println(m);
		}

		// Запрос можно задать также, при помощи criteriaBuilder который показан ниже...
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
		criteriaQuery.distinct(true); // Если этот параметер не установить, то к каждой записи из adress будет
										// присоединена запись - customer.
		Root<Customer> customer = criteriaQuery.from(Customer.class);
		customer.fetch("extension", JoinType.LEFT);
		customer.fetch("type", JoinType.LEFT);
		customer.fetch("adress", JoinType.LEFT);
		criteriaQuery.select(customer);

		query = entityManager.createQuery(criteriaQuery);
		resultList = query.getResultList();
		assertEquals("Customer count", 4, resultList.size());

		System.out.println("Customer records count: " + resultList.size());
		for (Customer m : resultList) {
			System.out.println(m);
		}
		entityManager.close();
	}

	@Test
	public void criteriaBuilderSample() {

		final EntityManager entityManager = factory.createEntityManager();

		// Запрос можно задать также, при помощи criteriaBuilder который показан ниже...
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
		criteriaQuery.distinct(true); // Если этот параметер не установить, то к каждой записи из adress будет
										// присоединена запись - customer.
		Root<Customer> customer = criteriaQuery.from(Customer.class);
		customer.fetch("extension", JoinType.LEFT);
		customer.fetch("type", JoinType.LEFT);
		customer.fetch("adress", JoinType.LEFT);
		criteriaQuery.select(customer);

		TypedQuery<Customer> query = entityManager.createQuery(criteriaQuery);
		List<Customer> resultList = query.getResultList();
		assertEquals("Customer count", 4, resultList.size());

		System.out.println("Customer records count: " + resultList.size());
		for (Customer m : resultList) {
			System.out.println(m);
		}
		
		entityManager.close();
	}
	
	
	@Test
	public void criteriaBuilderWhereSample() {

		final EntityManager entityManager = factory.createEntityManager();

		// Запрос можно задать также, при помощи criteriaBuilder который показан ниже...
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
		criteriaQuery.distinct(true);
		
		Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
		
		criteriaQuery.select(customerRoot);
		
		criteriaQuery.where(criteriaBuilder.equal(customerRoot.get("firstName"), "John"));
		
		TypedQuery<Customer> query = entityManager.createQuery(criteriaQuery);
		List<Customer> resultList = query.getResultList();

		System.out.println("Customer records count: " + resultList.size());
		for (Customer m : resultList) {
			System.out.println(m);
		}
		
		entityManager.close();
	}

	private void createInitialCustomers() {
		final EntityManager em = factory.createEntityManager();
		
		try {
			final EntityTransaction transaction = em.getTransaction();
			transaction.begin();

			CustomerType customerType = new CustomerType("student", "Customer is Student");
			em.merge(customerType);

			Customer c1 = new Customer("Jack", "Bauer", customerType);
			c1.setExtension(new CustomerExtension("addtionalData1"));
			c1.addAdress(new Adress("1_line1", "1_line2"));
			c1.addAdress(new Adress("2_line1", "2_line2"));
			
			Customer c2 = new Customer("Nikolas", "Cage", customerType);
			c2.setExtension(new CustomerExtension("addtionalData2"));
			
			Customer c3 = new Customer("Piter", "First", null);
			c3.setExtension(new CustomerExtension("addtionalData3"));
			
			Customer c4 = new Customer("John", "Rembo", customerType);
			c4.setExtension(new CustomerExtension("addtionalData4"));
			
			Arrays.asList(c1, c2, c2, c4).forEach(em::merge);

			transaction.commit();
		} finally {
			Optional.ofNullable(em).ifPresent(t-> t.close());
		}

	}


}
