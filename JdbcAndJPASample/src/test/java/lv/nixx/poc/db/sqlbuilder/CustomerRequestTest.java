package lv.nixx.poc.db.sqlbuilder;


import lv.nixx.poc.db.domain.Customer;
import lv.nixx.poc.db.domain.CustomerType;
import lv.nixx.poc.db.domain.CustomerWithType;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class CustomerRequestTest {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    @Before
    public void init() {
        final EntityManager entityManager = factory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("DELETE FROM Customer").executeUpdate();
        entityManager.createQuery("DELETE FROM CustomerType").executeUpdate();

        transaction.commit();
        entityManager.close();

        insertData();
    }

    @Test
    public void customerRequestByFistLastNameTest() {

        final EntityManager em = factory.createEntityManager();

        try {
            Collection<CustomerWithType> result = CustomerRequest.create()
                    .withFirstName("Name1")
                    .withLastName("LastName1")
                    .execute(em);

            assertEquals(1, result.size());

        } finally {
            em.close();
        }
    }

    @Test
    public void customerRequestByTypeTest() {

        final EntityManager em = factory.createEntityManager();

        try {
            Collection<CustomerWithType> result = CustomerRequest.create()
                    .withFirstName(null)
                    .withLastName(null)
                    .withType(Arrays.asList("massMarket", "student"))
                    .execute(em);

            result.forEach(System.out::println);

            assertEquals(5, result.size());

        } finally {
            em.close();
        }
    }


    private void insertData() {

        final EntityManager entityManager = factory.createEntityManager();

        try {
            EntityTransaction transaction = entityManager.getTransaction();

            transaction.begin();

            CustomerType studentType = new CustomerType("student", "Customer is Student");
            CustomerType managerType = new CustomerType("managerType", "Customer is manager");
            CustomerType massMarket = new CustomerType("massMarket", "Customer is mass market");

            entityManager.persist(studentType);
            entityManager.persist(managerType);
            entityManager.persist(massMarket);

            Arrays.asList(
                    new Customer("Name1", "LastName1", studentType),
                    new Customer("Name2", "LastName2", massMarket),
                    new Customer("Name3", "LastName3", managerType),
                    new Customer("Name4", "LastName4", studentType),
                    new Customer("Name4", "LastName41", massMarket),
                    new Customer("Name5", "LastName5", massMarket)
            ).forEach(entityManager::persist);
            transaction.commit();

        } finally {
            entityManager.close();
        }

    }

}
