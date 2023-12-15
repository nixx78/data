package lv.nixx.poc.db.embedded;

import lv.nixx.poc.db.TablePrinter;
import lv.nixx.poc.db.domain.embedded.AddressEmbeddable;
import lv.nixx.poc.db.domain.embedded.CustomerWithEmbeddedAddress;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.*;
import java.util.Optional;

public class CustomerWithEmbeddedAddressTest {

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    TablePrinter customerPrinter = new TablePrinter("CUSTOMER_WITH_EMBEDDED_ADDRESS");
    @BeforeEach
    void init() {
        clearDatabase();
    }

    private void clearDatabase() {
        final EntityManager em = factory.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.createQuery("DELETE FROM CustomerWithEmbeddedAddress").executeUpdate();
            transaction.commit();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();

            transaction.rollback();
        } finally {
            Optional.of(em).ifPresent(EntityManager::close);
        }
    }

    @AfterEach
    void destroy() {
        factory.close();
    }

    @Test
    void storeAndGetEmbeddedValues() {
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();

        CustomerWithEmbeddedAddress c1 = CustomerWithEmbeddedAddress.builder()
                .firstAddress(new AddressEmbeddable("1_first_Line1", "1_first_Line2"))
                .secondAddress(new AddressEmbeddable("1_second_Line1", "1_second_Line2"))
                .build();

        CustomerWithEmbeddedAddress c2 = CustomerWithEmbeddedAddress.builder()
                .firstAddress(new AddressEmbeddable("2_first_Line1", "2_first_Line2"))
                .secondAddress(new AddressEmbeddable("2_second_Line1", "2_second_Line2"))
                .build();

        // В этом случае, объект AddressEmbeddable будет null
        CustomerWithEmbeddedAddress c3 = CustomerWithEmbeddedAddress.builder()
                .firstAddress(null)
                .secondAddress(null)
                .build();

        CustomerWithEmbeddedAddress c4 = CustomerWithEmbeddedAddress.builder()
                .firstAddress(new AddressEmbeddable("4_first_Line1", "4_first_Line2"))
                .build();


        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);


        em.getTransaction().commit();

        customerPrinter.print();

        System.out.println("---- Entities from table -----");
        TypedQuery<CustomerWithEmbeddedAddress> t = em.createQuery("select c from CustomerWithEmbeddedAddress c", CustomerWithEmbeddedAddress.class);
        t.getResultStream().forEach(System.out::println);
        System.out.println("-------------------------------");

        em.close();
    }

}
