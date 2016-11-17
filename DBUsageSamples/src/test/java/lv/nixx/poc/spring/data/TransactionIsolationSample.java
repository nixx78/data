package lv.nixx.poc.spring.data;

import static org.junit.Assert.*;

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
		System.out.println("!!!! id " + id);
		
		entityManager.clear();
		
		tryFindInAnotherTransaction(id);
	}
	
	private void tryFindInAnotherTransaction(Long id) {
		EntityManager em = entityManagerFactory.createEntityManager();
		
	    Query query = em.createQuery("SELECT p FROM Person p");
	    List<Person> resultList = (List<Person>) query.getResultList();
	    for(Person p: resultList){
	    	System.out.println("!!! " + p);
	    }

		
//		Person foundPerson = em.find(Person.class, id);
//		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//		System.out.println(foundPerson);
	}


}
