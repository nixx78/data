package lv.nixx.poc.db.jpa;

import lv.nixx.poc.db.TablePrinter;
import lv.nixx.poc.db.domain.*;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerPersistSample {

	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("simple.customer.unit");

	@Test
	public void testShouldPersistCustomerWithAllAttributes() throws Exception {

		EntityManager em = factory.createEntityManager();
		final EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		CustomerType type1 = new CustomerType("TYPE1", "genericType1");
		em.persist(type1);

		Customer customer = new Customer("name1", "surname1", null);
		customer.setExtension(new CustomerExtension("additionalData"));
		customer.addAddress(new Address("a.line11", "a.line12"));
		customer.addAddress(new Address("a.line21", "a.line22"));
		customer.addAddress(new Address("a.line21", "a.line224444"));
		customer.setType(type1);
		customer.setSegment(Segment.VIP);

		em.persist(customer);

		final Long id = customer.getId();

		assertNotNull(id); // проверяем, что ID сгенерировано для Customer
		transaction.commit();
		em.close();

		// Получаем  entity еще раз из базы данных
		em = factory.createEntityManager();

		final Customer savedCustomer = em.find(Customer.class, id);

		assertNotNull(savedCustomer.getExtension());
		assertEquals(3, savedCustomer.getAddress().size());
		assertEquals("TYPE1", savedCustomer.getType().getId());
		assertEquals(Segment.VIP, savedCustomer.getSegment());


		new TablePrinter("CUSTOMER").print();
		new TablePrinter("ADDRESS").print();
		new TablePrinter("CUSTOMER_EXTENSION").print();
		new TablePrinter("CUSTOMER_TYPE").print();


	}



}
