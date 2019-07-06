package lv.nixx.poc.db.jpa;

import lv.nixx.poc.db.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;


public class NamedNativeQuerySample {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    @Before
    public void init() {
        final EntityManager entityManager = factory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("DELETE FROM Customer").executeUpdate();
        entityManager.createQuery("DELETE FROM CustomerType").executeUpdate();

        transaction.commit();
        entityManager.close();
    }

    @After
    public void destroy() {
        factory.close();
    }

    @Test
    public void nativeNamedQueryExample() {
        final EntityManager entityManager = factory.createEntityManager();

        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        CustomerType customerType = new CustomerType("student", "Customer is Student");
        entityManager.merge(customerType);

        Customer c1 = new Customer("Jack", "Bauer", customerType);
        c1.setExtension(new CustomerExtension("addtionalData1"));
        c1.addAddress(new Address("1_line1", "1_line2"));
        c1.addAddress(new Address("2_line1", "2_line2"));
        Customer c2 = new Customer("Nikolas", "Cage", customerType);
        c2.setExtension(new CustomerExtension("addtionalData2"));
        Customer c3 = new Customer("Piter", "First", null);
        c3.setExtension(new CustomerExtension("addtionalData3"));
        Customer c4 = new Customer("John", "Rembo", customerType);
        c4.setExtension(new CustomerExtension("addtionalData4"));

        entityManager.merge(c1);
        entityManager.merge(c2);
        entityManager.merge(c3);
        entityManager.merge(c4);

        transaction.commit();

        List<CustomerWithType> result = entityManager.createNamedQuery("customersWithType", CustomerWithType.class).getResultList();
        result.forEach(System.out::println);


        entityManager.close();
    }

    //TODO ResultSet transformer sample
//    List postDTOs = entityManager
//            .createNativeQuery(
//                    "select " +
//                            "       p.id as \"id\", " +
//                            "       p.title as \"title\" " +
//                            "from Post p " +
//                            "where p.created_on > :fromTimestamp")
//            .setParameter( "fromTimestamp", Timestamp.from(
//                    LocalDateTime.of( 2016, 1, 1, 0, 0, 0 ).toInstant( ZoneOffset.UTC ) ))
//            .unwrap( org.hibernate.query.NativeQuery.class )
//            .setResultTransformer( Transformers.aliasToBean( PostDTO.class ) )
//            .getResultList();


}
