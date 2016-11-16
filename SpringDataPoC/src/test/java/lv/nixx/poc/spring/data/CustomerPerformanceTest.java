package lv.nixx.poc.spring.data;

import java.util.*;

import javax.persistence.*;
import javax.transaction.Transactional;

import lv.nixx.poc.spring.data.domain.*;
import lv.nixx.poc.spring.data.repository.*;

import org.junit.Ignore;
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
public class CustomerPerformanceTest {
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private CustomerExtensionRepository customerExtensionRepository;

	@Autowired
	private EntityManager entityManager;

	private final int recordCount = 10000;

	@Test
	@Ignore
	public void saveAndRetrieveData(){
		clearAllRecords();
		fillCustomerData();

		// Обязательно, нужно делать clear - иначе, данные не будут братся из таблиц, 
		// тест не будет полноценным
		entityManager.clear();
		findAllDataRepositoryFindAll();
		
		entityManager.clear();
		findAllDataUsingLeftJoin();
	}
	
	public void fillCustomerData() {
		final CustomerType simpleCustomer = new CustomerType("simple","Simple Customer");
		typeRepository.save(simpleCustomer);
		
		final long startTime = System.currentTimeMillis();
		
		// Create and save multiply customers
		List<Customer> customers = new ArrayList<>(recordCount);
		for (int i = 0; i < recordCount; i++) {
			customers.add(new Customer("name", "surname", simpleCustomer, new CustomerExtension("additionalData")));
		}
		customerRepository.save(customers ); 
		System.out.println("[" + recordCount + "] insertion done by [" + (System.currentTimeMillis()-startTime) + "] milleseconds");
		entityManager.flush();
	}

	private void clearAllRecords() {
		customerRepository.deleteAll();
		typeRepository.deleteAll();
		entityManager.flush();
	}
	
	public void findAllDataUsingLeftJoin() {
		final long startTime = System.currentTimeMillis();
		customerRepository.findAllCustomers().size();
		System.out.println("[" + recordCount + "] records retrieved by [" + (System.currentTimeMillis()-startTime) + "] milleseconds using LEFT JOIN");
	}

	public void findAllDataRepositoryFindAll() {
		final long startTime = System.currentTimeMillis();
		customerRepository.findAll();
		System.out.println("[" + recordCount + "] records retrieved by [" + (System.currentTimeMillis()-startTime) + "] milleseconds using findAll()");
	}

}

