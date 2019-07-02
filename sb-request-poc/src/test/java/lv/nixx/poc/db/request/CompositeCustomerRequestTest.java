package lv.nixx.poc.db.request;

import lv.nixx.poc.db.mapping.CustomerWithType;
import lv.nixx.poc.db.util.TestDataCreator;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class CompositeCustomerRequestTest {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    private TestDataCreator testDataCreator = new TestDataCreator();

    @Before
    public void init() {
        testDataCreator.clearData();
        testDataCreator.createTestData();
    }

    @Test
    public void customerRequestByFistLastNameTest() {

        final EntityManager em = factory.createEntityManager();

        try {
            Collection<CustomerWithType> result = CompositeCustomerRequest.create()
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
            Collection<CustomerWithType> result = CompositeCustomerRequest.create()
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

}
