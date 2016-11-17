package lv.nixx.poc.spring.data;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import lv.nixx.poc.spring.data.domain.*;
import lv.nixx.poc.spring.data.repository.PersonRepository;

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
public class PersonTest {
	
	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private EntityManager entityManager;

	@Before
	public void init() {
		personRepository.deleteAll();
		entityManager.flush();
	}
	
	@Test
	public void createPersonsWithRelatedObjects() {
		
		Person p1 = new Person("Ivan", "Ivanov", new PersonExtension("1_line1", "1_line2"));
		p1.addAliase("alias1");
		p1.addAliase("alias2");
		p1.addAliase("alias3");
		p1.addAdditionalField(new PersonAdditionalField("p1_f11", "p1_f12"));
		p1.addAdditionalField(new PersonAdditionalField("p1_f21", "p1_f22"));
		
		Person p2 = new Person("John", "Smith", new PersonExtension("2_line1", "2_line2"));
		p2.addAdditionalField(new PersonAdditionalField("p2_f11", "p2_f12"));
		p2.addAdditionalField(new PersonAdditionalField("p2_f21", "p2_f22"));
		p2.addAdditionalField(new PersonAdditionalField("p2_f31", "p2_f32"));
		p2.addTask(new Task("tID1", "projectA", " task1"));
		p2.addTask(new Task("tID2", "projectB", " task2"));
		
		Person p3 = new Person("Anna", "Smith", null);
		p3.addAdditionalField(new PersonAdditionalField("p3_f11", "p3_f12"));
		p3.addAdditionalField(new PersonAdditionalField("p3_f21", "p3_f22"));

		p3.addTask(new Task("tID1", "projectA", " task1"));
		p3.addTask(new Task("tID2", "projectB", " task2"));
		p3.addTask(new Task("tID3", "projectB", " task3"));
		p3.addTask(new Task("tID4", "projectA", " task4"));

		personRepository.save(p1);
		personRepository.save(p2);
		Long p2Id = p2.getId();
		personRepository.save(p3);

		entityManager.flush();

		personRepository.delete(p2);
		entityManager.flush();
		
		Iterable<Person> findAll = personRepository.findAll();
		for (Person person : findAll) {
			System.out.println(person);
			System.out.println("\t\t Aliases:" + person.getAliases());
			System.out.println("\t\t Fields:" + person.getAdditionalFields());
		}

		Long p1Id = p1.getId();
		Long p3Id = p3.getId();

		p1 = personRepository.findOne(p1Id);
		assertNotNull(p1);
		assertNotNull(p1.getExtension());
		Set<PersonAdditionalField> additionalFields = p1.getAdditionalFields();
		assertNotNull(additionalFields);
		assertEquals(2, additionalFields.size());
		
		p2 = personRepository.findOne(p2Id);
		assertNull(p2);

		p3 = personRepository.findOne(p3Id);
		assertNotNull(p3);
		assertNull(p3.getExtension());

		// check tasks
		Map<String, Task> tasks = p3.getTasks();
		assertNotNull(tasks);
		assertEquals(4, tasks.size());
		
		assertNotNull(tasks.get("tID1"));
		assertNotNull(tasks.get("tID2"));
		assertNotNull(tasks.get("tID3"));
		assertNotNull(tasks.get("tID4"));
		
		for (Task t: tasks.values()){
			System.out.println(t);
		}
	}

}
