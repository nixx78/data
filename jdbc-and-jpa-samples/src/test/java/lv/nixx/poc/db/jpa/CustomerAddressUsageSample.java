package lv.nixx.poc.db.jpa;

import lv.nixx.poc.db.TablePrinter;
import lv.nixx.poc.db.domain.Address;
import lv.nixx.poc.db.domain.Customer;
import lv.nixx.poc.db.domain.CustomerExtension;
import lv.nixx.poc.db.domain.CustomerType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

public class CustomerAddressUsageSample {

	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

	TablePrinter customerPrinter = new TablePrinter("CUSTOMER");
	TablePrinter addressPrinter = new TablePrinter("ADDRESS");

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
			em.createQuery("DELETE FROM CustomerType").executeUpdate();
			em.createQuery("DELETE FROM CustomerExtension").executeUpdate();
			em.createQuery("DELETE FROM Address").executeUpdate();
			em.createQuery("DELETE FROM Customer").executeUpdate();
			transaction.commit();
			
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
			
			transaction.rollback();
		} finally {
			Optional.of(em).ifPresent(EntityManager::close);
		}
	}

	@After
	public void destroy() {
		factory.close();
	}

	@Test
	public void changeAddressSample() {
		EntityManager em = factory.createEntityManager();
		TypedQuery<Customer> query = em.createNamedQuery("Customer.findCustomerByLastName", Customer.class);
		query.setParameter("lastName", "Bauer");
		Customer customer = query.getSingleResult();

		customerPrinter.print();
		addressPrinter.print();

		em.getTransaction().begin();

		Set<Address> address = customer.getAddress();

		Address firstAddress = address.iterator().next();
		firstAddress.setLine2("Updated.line");
		address.clear();

		address.add(firstAddress);
		address.add(new Address("New add11", "New Add12"));
		address.add(new Address("New add21", "New Add22"));

		// Persist не нужен, объект у нас уже в контексте
		em.getTransaction().commit();

		System.out.println("Data in tables after update:");

		customerPrinter.print();
		addressPrinter.print();
	}

	private void createInitialCustomers() {
		final EntityManager em = factory.createEntityManager();
		
		try {
			final EntityTransaction transaction = em.getTransaction();
			transaction.begin();

			CustomerType customerType = new CustomerType("student", "Customer is Student");
			em.persist(customerType);

			Customer c1 = new Customer("Jack", "Bauer", customerType);
			c1.setExtension(new CustomerExtension("additionalData1"));
			c1.addAddress(new Address("1_line1", "1_line2"));
			c1.addAddress(new Address("2_line1", "2_line2"));

			Arrays.asList(c1).forEach(em::persist);

			transaction.commit();
		} finally {
			Optional.ofNullable(em).ifPresent(EntityManager::close);
		}

	}


}
