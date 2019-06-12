package lv.nixx.poc.db.jpa;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.*;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lv.nixx.poc.db.domain.*;


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
        c1.addAdress(new Adress("1_line1", "1_line2"));
        c1.addAdress(new Adress("2_line1", "2_line2"));
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


    @Test
    public void sqlBuilderSample() {

        final EntityManager entityManager = factory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
            Arrays.asList(
                    new Customer("Name1", "LastName1", null),
                    new Customer("Name2", "LastName2", null),
                    new Customer("Name3", "LastName3", null),
                    new Customer("Name4", "LastName4", null)
            ).forEach(entityManager::persist);
        transaction.commit();


        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();

        DbTable customerTable = schema.addTable("Customer");
        DbColumn firstNameColumn = customerTable.addColumn("firstName");
        DbColumn lastNameColumn = customerTable.addColumn("lastName");

        SelectQuery query = new SelectQuery()
                .addAllColumns()
                .addCondition(BinaryCondition.equalTo(firstNameColumn, "Name1"))
                .addFromTable(customerTable).validate();

        String lastNameParam = "LastName1";

        // we can add condition later
        Optional.ofNullable(lastNameParam).ifPresent( p ->
                query.addCondition(BinaryCondition.equalTo(lastNameColumn, p))
        );

        String s = query.toString();

        System.out.println("==========================");
        System.out.println(s);
        System.out.println("==========================");

        List<Object[]> resultList = entityManager.createNativeQuery(s).getResultList();

        resultList.stream()
                .map(Arrays::toString)
                .forEach(System.out::println);

    }

}
