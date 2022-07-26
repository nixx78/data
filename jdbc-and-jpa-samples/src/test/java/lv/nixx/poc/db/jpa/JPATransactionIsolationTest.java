package lv.nixx.poc.db.jpa;

import java.util.*;
import javax.persistence.*;

import org.junit.*;

import lv.nixx.poc.db.domain.Person;

public class JPATransactionIsolationTest {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("test.unit");
	
	@Before
	public void init() {
		// Данные настройки работают только если используется EmbeddedDerby
		System.setProperty("derby.locks.deadlockTimeout","2");
		System.setProperty("derby.locks.waitTimeout","2");
		
		// http://db.apache.org/derby/docs/10.5/ref/rrefproperrowlocking.html#rrefproperrowlocking
		//System.setProperty("derby.storage.rowLocking", "true");

		EntityManager entityManager = emf.createEntityManager();

		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// Данные удаляем именно из таблиц, ибо, если удалять из сущности Person
		// получаем constrain error
		entityManager.createNativeQuery("DELETE FROM TASK").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM ALIAS_TABLE").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM PERSON_ADDITIONAL_FIELD").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM PERSON").executeUpdate();
		transaction.commit();

		entityManager.close();
	}
	
	@Test
	public void dirtyReadTransactionIsolation(){
		EntityManager em = emf.createEntityManager();
		
		final EntityTransaction txn = em.getTransaction();
		txn.begin();
		
			Person pers = new Person("Name1" + new Date(), "Surname1", null);
			Long id = em.merge(pers).getId();
			System.out.println("created person id: " + id);
		
			try {
				tryToSelectAllRecords(id);
			} catch (Exception ex){
				// тут мы получаем ошибку, из-за того, что стоит лок на все таблицу а не 
				// на конкретную запись, это зависит от реализации базы данных
				System.err.println(ex);
			}
			
		txn.commit();
	}
	
	private void tryToSelectAllRecords(Long id) {
		EntityManager em1 = emf.createEntityManager();
		
	    TypedQuery<Person> query = em1.createQuery("SELECT p FROM Person p", Person.class);
	    List<Person> resultList = query.getResultList();
	    resultList.forEach(System.out::println);
	}


}
