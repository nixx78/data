package lv.nixx.poc.spring.data.jpa;

import javax.persistence.*;

import lv.nixx.poc.spring.data.domain.Person;

import org.junit.*;


public class L2CasheUsageSample {
	
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");
	
	@Before
	public void deleteExistingData() {
		
		EntityManager entityManager = factory.createEntityManager();

		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// Данные удаляем именно из таблиц, ибо, если удалять из сущности Person
		// получаем constrain error
		entityManager.createNativeQuery("DELETE FROM Task").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM Aliase").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM PersonAdditionalField").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM Person").executeUpdate();
		transaction.commit();

		entityManager.close();
	}
	
	@Test
	public void entityPersonShouldBeCashed(){
		EntityManager em = factory.createEntityManager();
		em.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
		em.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.USE);
		 
		final EntityTransaction txn = em.getTransaction();
		
		txn.begin();
		Long id = em.merge(new Person("Name1", "Surname1", null)).getId();
		txn.commit();
		
		System.out.println("personid " + id );
		
		Cache cache = factory.getCache();
		
		boolean isCached = cache.contains(Person.class, id);
		//assertTrue(isCached);
		
		Person foundPerson = em.find(Person.class, id);
		System.out.println(foundPerson);
	}
	

}
