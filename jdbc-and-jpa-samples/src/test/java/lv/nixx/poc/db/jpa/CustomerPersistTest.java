package lv.nixx.poc.db.jpa;

import lv.nixx.poc.db.TablePrinter;
import lv.nixx.poc.db.domain.*;
import org.junit.jupiter.api.Test;

import javax.persistence.*;

import static org.junit.jupiter.api.Assertions.*;


public class CustomerPersistTest {

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("simple.customer.unit");

    private final TablePrinter customerTablePrinter = new TablePrinter("CUSTOMER");

    @Test
    void testShouldPersistCustomerWithAllAttributes() {

        EntityManager em = factory.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        CustomerType type1 = new CustomerType("TYPE1", "genericType1");
        em.persist(type1);

        Customer customer = new Customer("name1", "surname1", null)
                .setExtension(new CustomerExtension("additionalData"))
                .addAddress(new Address("a.line11", "a.line12"))
                .addAddress(new Address("a.line21", "a.line22"))
                .addAddress(new Address("a.line21", "a.line224444"))
                .setType(type1)
                .setSegment(Segment.VIP);

        em.persist(customer);

        final Long id = customer.getId();

        assertNotNull(id); // проверяем, что ID сгенерировано для Customer
        transaction.commit();
        em.close();

        // Получаем  entity еще раз из базы данных
        em = factory.createEntityManager();

        final Customer savedCustomer = em.find(Customer.class, id);

        assertAll(
                () -> assertNotNull(savedCustomer.getExtension()),
                () -> assertEquals(3, savedCustomer.getAddress().size()),
                () -> assertEquals("TYPE1", savedCustomer.getType().getId()),
                () -> assertEquals(Segment.VIP, savedCustomer.getSegment())
        );

        customerTablePrinter.print();
        new TablePrinter("ADDRESS").print();
        new TablePrinter("CUSTOMER_EXTENSION").print();
        new TablePrinter("CUSTOMER_TYPE").print();
    }

    @Test
    void saveAndUpdateCustomerUsingUpdateStatement() {

        EntityManager em = factory.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer("name1", "surname1", null);
        Customer savedCustomer = em.merge(customer);

        transaction.commit();

        // Update customer
        System.out.println("--------------------------------");
        em.getTransaction().begin();
        Query query = em.createQuery("UPDATE Customer c SET c.lastName = :lstName WHERE c.id=:id");
        query.setParameter("lstName", "updatedLastName");
        query.setParameter("id", savedCustomer.getId());

        int rowsUpdated = query.executeUpdate();
        System.out.println("entities Updated: " + rowsUpdated);
        em.getTransaction().commit();
        em.close();

        final Customer savedCustomer1 = factory.createEntityManager().find(Customer.class, savedCustomer.getId());
        assertEquals("updatedLastName", savedCustomer1.getLastName());

        customerTablePrinter.print();
    }

    @Test
    void saveAndUpdateCustomerUsingUpdateEntity() {

        EntityManager em = factory.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer("name1", "surname1", null);
        customer.setSegment(Segment.VIP);
        Customer savedCustomer = em.merge(customer);

        transaction.commit();

        // Update customer
        System.out.println("--------------------------------");

        CustomerEntityForUpdate customerEntityForUpdate = em.find(CustomerEntityForUpdate.class, savedCustomer.getId());

        em.getTransaction().begin();
        customerEntityForUpdate.setLastName("LastNameOneMoreTimeUpdated");
        em.getTransaction().commit();
        em.close();

        customerTablePrinter.print();

        final Customer savedCustomer1 = factory.createEntityManager().find(Customer.class, savedCustomer.getId());
        assertEquals("LastNameOneMoreTimeUpdated", savedCustomer1.getLastName());
    }

}
