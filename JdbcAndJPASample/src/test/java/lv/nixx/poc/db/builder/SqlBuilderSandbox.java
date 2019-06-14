package lv.nixx.poc.db.builder;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.CustomSql;
import com.healthmarketscience.sqlbuilder.InCondition;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lv.nixx.poc.db.domain.Customer;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SqlBuilderSandbox {

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
        Optional.ofNullable(lastNameParam).ifPresent(p ->
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

    @Test
    public void sqlBuilderSample1() {

        final EntityManager entityManager = factory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Arrays.asList(
                new Customer("Name1", "LastName1", null),
                new Customer("Name2", "LastName2", null),
                new Customer("Name3", "LastName3", null),
                new Customer("Name4", "LastName4", null),
                new Customer("Name4", "LastName41", null)
        ).forEach(entityManager::persist);
        transaction.commit();


        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();

        DbTable customerTable = schema.addTable("Customer");
        DbColumn firstNameColumn = customerTable.addColumn("firstName");


        SelectQuery maxSelect = new SelectQuery()
                .addCustomColumns(new CustomSql("max(firstName)"))
                .addFromTable(customerTable)
                .validate();

        SelectQuery query = new SelectQuery()
                .addAllColumns()
                .addFromTable(customerTable)
                .addCondition(new InCondition(firstNameColumn, maxSelect))
                .validate();


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
