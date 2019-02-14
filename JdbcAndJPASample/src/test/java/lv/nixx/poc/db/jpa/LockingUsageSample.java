package lv.nixx.poc.db.jpa;

import static org.junit.Assert.*;

import javax.persistence.*;

import org.junit.*;

import lv.nixx.poc.db.domain.Person;

public class LockingUsageSample {
	
	private static final String initialPersonName = "initial.Name";
	private static final String changedPersonName = "new.Name";

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit", null);
	
	@BeforeClass
	public static void initProperties(){
		System.setProperty("derby.locks.deadlockTimeout","2");
		System.setProperty("derby.locks.waitTimeout","2");
	}
	
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
	public void changeNotLockedEntity() {
		final Long id = createMainDataEntity();
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction tx1 = entityManager.getTransaction();
		tx1.begin();

		Person md = entityManager.find(Person.class, id);
		assertEquals("Entity version not correct", 0L, md.getVersion());
		
		// Удачно поменяли запись в другой транзакции и закомитили
		changeMainDataInTransaction(md.getId());
		tx1.commit();
		entityManager.refresh(md);
		
		// Проверили, что данные соотвествуют тем, которые записали во второй раз
		// данные, которые мы записалив первый раз, но не закомитили, потерялись
		assertEquals(md.getName() , changedPersonName );
	}

	
	@Test(expected = RollbackException.class)
	public void changeOptimisticallyLockedEntityAndGetException() {
		try {
			final Long id = createMainDataEntity();

			EntityManager entityManager = factory.createEntityManager();
			EntityTransaction tx1 = entityManager.getTransaction();
			tx1.begin();

			// Найдем данные в базе и установим lock, его можно также установить при 
			// помощи конструкции: entityManager.lock(md, LockModeType.OPTIMISTIC); 
			Person p = entityManager.find(Person.class, id, LockModeType.OPTIMISTIC);
			
			assertEquals("Entity version not correct", 0, p.getVersion());

			// Изменим данные в другой транзакции и закомитим их
			changeMainDataInTransaction(p.getId());

			// При commit первой транзакции мы получаем ошибку, поскольку есть более новая
			// версия объекта, которую мы сохранили в другой тразакции
			tx1.commit();

		} catch (RollbackException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test(expected = RollbackException.class)
	public void changePesimisticLockedObjectAndGetException() {
		final Long id = createMainDataEntity();
		
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction tx1 = entityManager.getTransaction();
		tx1.begin();

		// Найдем cущность и установим Pesimistic lock, для Derby работает только ReadLock, при попытки установить
		// WriteLock происходит ошибка, поскольку конструкция Select For Update отсутсвует
		entityManager.find(Person.class, id, LockModeType.PESSIMISTIC_READ);
	
		try {
			// попробуем изменить данные в другой транзакции
			// в ней должна произойти ошибка, посколько несмоги взять Lock
			changeMainDataInTransaction(id);
		} finally {
			// если этого не сделать, то может падать другой тест, поскольку, на записи будет стоять lock
			tx1.commit();
		}
	}

	private void changeMainDataInTransaction(Long id) {
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction tx2 = entityManager.getTransaction();
		tx2.begin();

		Person p = entityManager.find(Person.class, id);
		
		p.setName(changedPersonName);
		entityManager.persist(p);
		
		tx2.commit();
		
		entityManager.refresh(p);
		assertEquals("Entity version not correct", 1, p.getVersion());
		entityManager.close();
	}


	private Long createMainDataEntity() {
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		
		Person md = new Person(initialPersonName, "surname", null);
		Long id = entityManager.merge(md).getId();
		System.out.println("Person with id created [" + id + "]");
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return id;
	}
	

}

