package lv.nixx.poc.db.request;

import lv.nixx.poc.db.domain.Customer;
import lv.nixx.poc.db.util.TestDataCreator;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerRequestTest {

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
            List<Customer> result = CustomerRequest.create()
                    .withFirstName("Name1")
                    .withLastName("LastName1")
                    .execute(em);

            assertEquals(1, result.size());

            System.out.println(result);

        } finally {
            em.close();
        }
    }



}
