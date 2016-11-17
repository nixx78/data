package lv.nixx.poc.spring.data;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.transaction.Transactional;

import lv.nixx.poc.spring.data.domain.*;
import lv.nixx.poc.spring.data.repository.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPAConfiguration.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class CustomerSpringDataTest {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private CustomerExtensionRepository customerExtensionRepository;
	@Autowired
	private AdressRepository adressRepository;

	@Autowired
	private CustomerDAO simpleDao;

	@Autowired
	private EntityManager entityManager;
	
	@Before
	public void prepateTables(){
		adressRepository.deleteAll();
		customerRepository.deleteAll();
		typeRepository.deleteAll();
		entityManager.flush();
	}

	@Test
	public void testCustomerTypeMultiplySave() {
		
		/*
		 * В данном тесте мы сохраняем объект с одним идентификатором несколько раз, 
		 * также, мы пытаемся его изменить. При проверке мы удостоверяемся, что при
		 * извлечении из базы мы видим последние изменения. 
		 */
		
		final String testId = "testId";
		final CustomerType testType = new CustomerType(testId, "TestType");
		typeRepository.save(testType);
		entityManager.flush();

		testType.setDescription("New TestType desciption");
		typeRepository.save(testType);
		entityManager.flush();
		
		final CustomerType test1Type = new CustomerType(testId, "Last TestType description");
		typeRepository.save(test1Type);
		entityManager.flush();
		
		CustomerType t = typeRepository.findOne(testId);
		assertNotNull(t);
		assertEquals(testId, t.getId());
		assertEquals("Last TestType description", t.getDescription());

		printCustomerTypes();
	}
	
	@Test
	public void testMultiplyCustomerSave(){
		
		/* В данном тесте, мы создаем двух клиентов с одним и темже типом, при проверяем, что не возникает ошибка
		 * с дубликатом CustomerType.
		 */

		final CustomerType simpleCustomer = new CustomerType("simple","Simple Customer");
		/* Если убрать строку:
		 * typeRepository.save(simpleCustomer);
		 * 
		 * то будет ошибка:
		 * java.lang.IllegalStateException: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save 
		 * the transient instance before flushing : lv.nixx.poc.spring.data.domain.Customer.type -> lv.nixx.poc.spring.data.domain.CustomerType 
		 * 
		 * Она исправляется установкой в классе Customer: 
		 * @ManyToOne(cascade={CascadeType.PERSIST})
		 * Однако, в этом случае, будет проблема при создании повторно клиента с такимже типом
		 */
		
		typeRepository.save(simpleCustomer);
		entityManager.flush();
		
		Customer c1 = new Customer("Jack", "Bauer", simpleCustomer);
		simpleDao.save(c1);
		entityManager.flush();

		Long c1ID = c1.getId();
		assertNotNull(c1ID);

		Customer c2 = new Customer("Nikolas", "Cage", simpleCustomer);
		simpleDao.save(c2);
		entityManager.flush();
		Long c2ID = c2.getId();
		assertNotNull(c2ID);
		
		Customer exC1Customer = customerRepository.findOne(c1ID);
		assertNotNull(exC1Customer);
		assertEquals(new CustomerType("simple","Simple Customer"), exC1Customer.getType());

		Customer exC2Customer = customerRepository.findOne(c2ID);
		assertNotNull(exC2Customer);
		assertEquals(new CustomerType("simple","Simple Customer"), exC2Customer.getType());

		printCustomers();
	}
	
	@Test
	public void testMultiplyCustomerWithDifferentTypeSave() {

		final CustomerType simpleCustomer = new CustomerType("simple","Simple Customer");
		final CustomerType vipCustomer = new CustomerType("vip","Vip Customer");
		typeRepository.save(simpleCustomer);
		typeRepository.save(vipCustomer);
		entityManager.flush();
		
		// save a couple of customers
		Customer c1 = new Customer("Jack", "Bauer", simpleCustomer);
		c1.setExtension(new CustomerExtension("Jack's additional data"));
		Customer c2 = new Customer("Chloe", "O'Brian", vipCustomer);
		Customer c3 = new Customer("Nikolas", "Smith", simpleCustomer);
		c3.setExtension(new CustomerExtension("Nikolas's additional data"));
		c3.addAdress(new Adress("N1 line1", "N1 line2"));
		c3.addAdress(new Adress("N2 line1", "N2 line2"));

		simpleDao.save(c1);
		simpleDao.save(c2);
		simpleDao.save(c3);
		entityManager.flush();
		
		printCustomers();
		Long c1ID = c1.getId();
		Long c2ID = c2.getId();
		Long c3ID = c3.getId();
		
		Customer expCustomer1 = customerRepository.findOne(c1ID);
		CustomerExtension c1Extension = expCustomer1.getExtension();
		assertNotNull(expCustomer1);
		assertNotNull(c1Extension);
		assertEquals("Jack's additional data", c1Extension.getAdditionalData());

		Customer expCustomer2 = customerRepository.findOne(c2ID);
		assertNotNull(expCustomer2);
		assertNull(expCustomer2.getExtension());

		Customer expCustomer3 = customerRepository.findOne(c3ID);
		CustomerExtension c3Extension = expCustomer3.getExtension();
		Set<Adress> adress = expCustomer3.getAdress();
		
		assertNotNull(expCustomer3);
		assertNotNull(c3Extension);
		assertNotNull(adress);
		
		assertEquals("Nikolas's additional data", c3Extension.getAdditionalData());
		assertEquals(2, adress.size());
		
		// Удалим адресс и Extension у клиента
		expCustomer3.getAdress().clear();
		expCustomer3.setExtension(null);
		entityManager.flush();

		// Проверим, что он действительно удалился
		expCustomer3 = customerRepository.findOne(c3ID);
		assertNotNull(expCustomer3);
		adress = expCustomer3.getAdress();

		assertNotNull(adress);
		assertTrue(adress.isEmpty());
	 	assertNull(expCustomer3.getExtension());
	 	
	}

	@Test
	public void testShouldAddCustomerWithAdress() {

		final CustomerType simpleCustomer = new CustomerType("simple","Simple Customer");
		typeRepository.save(simpleCustomer);

		Customer c1 = new Customer("Nikolas", "Smith", simpleCustomer);
		c1.setExtension(new CustomerExtension("Nikolas's additional data"));
		c1.addAdress(new Adress("N1 line1", "N1 line2"));
		c1.addAdress(new Adress("N2 line1", "N2 line2"));

		simpleDao.save(c1);
		entityManager.flush();
		
		printCustomers();
		Long c3ID = c1.getId();
		
		Customer expCustomer3 = customerRepository.findOne(c3ID);
		CustomerExtension c3Extension = expCustomer3.getExtension();
		Set<Adress> adress = expCustomer3.getAdress();
		
		assertNotNull(expCustomer3);
		assertNotNull(c3Extension);
		assertNotNull(adress);
		
		assertEquals("Nikolas's additional data", c3Extension.getAdditionalData());
		assertEquals(2, adress.size());
	}

	@Test
	public void testShouldRetrieveAndGetUsingNamedQuery(){
		
		final CustomerType simpleCustomer = new CustomerType("simple","Simple Customer");
		typeRepository.save(simpleCustomer);
		entityManager.flush();
		
		Customer c1 = new Customer("Jack", "Bauer", simpleCustomer);
		Customer c2 = new Customer("Nikolas", "Cage", simpleCustomer);
		simpleDao.save(c1);
		simpleDao.save(c2);
		
		List<Customer> result = customerRepository.selectAllCustomersUsingNamedQuery();
		assertEquals(2, result.size());
		
	}

	private void printCustomers() {
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		//for (Customer customer : customerRepository.findAll()) {
		for (Customer customer : customerRepository.findAllCustomers()) {
			System.out.println(customer);
		}
	}

	private void printCustomerTypes() {
		System.out.println("CustomerTypes found with findAll():");
		System.out.println("-------------------------------");
		for (CustomerType type : typeRepository.findAll()) {
			System.out.println(type);
		}
	}
}

