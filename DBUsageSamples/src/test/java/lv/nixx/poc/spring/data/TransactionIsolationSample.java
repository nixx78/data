package lv.nixx.poc.spring.data;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lv.nixx.poc.spring.data.domain.Person;
import lv.nixx.poc.spring.data.repository.PersonRepository;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPAConfiguration.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class TransactionIsolationSample {
	
	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@Before
	public void init() {
		System.setProperty("derby.locks.deadlockTimeout","2");
		System.setProperty("derby.locks.waitTimeout","2");

		personRepository.deleteAll();
	}
	
	@Test
	@Ignore
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void dirtyReadTransactionIsolation(){
		Person pers = new Person("Name1" + new Date(), "Surname1", null);
		personRepository.save(pers);
		
		Long id = pers.getId();

		entityManager.clear();
		
		tryFindInAnotherTransaction(id);
	}
	
	private void tryFindInAnotherTransaction(Long id) {
		EntityManager em = entityManagerFactory.createEntityManager();
		
		TypedQuery<Person> lQuery = em.createQuery("SELECT p FROM Person p", Person.class);
		List<Person> personList = lQuery.getResultList();
	    for(Person p: personList){
	    	System.out.println("!!! " + p);
	    }
	}


}
