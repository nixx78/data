package lv.nixx.poc.db;

import lv.nixx.poc.db.domain.Account;
import lv.nixx.poc.db.domain.Currency;
import lv.nixx.poc.db.domain.Transaction;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AccountTransactionSandbox {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    @Before
    public void init() {
        createTestData();
    }

    @Test
    public void test() {

        EntityManager em = factory.createEntityManager();
        List<Object[]> resultList = em.createNativeQuery("select * from TXN_TABLE").getResultList();
        resultList.stream()
                .map(Arrays::toString)
                .forEach(System.out::println);

        Account account = em.find(Account.class, 1L);
        System.out.println(account);
    }


    @Test
    public void accountWithMaxTxnTest() {

        EntityManager em = factory.createEntityManager();

        List<Object[]> resultList = em
                .createNativeQuery("select a.* from ACCOUNT a inner join (select ACCOUNT_ID, max(AMOUNT) as amt from TXN_TABLE group by ACCOUNT_ID order by amt desc " +
                        " fetch first 1 row only" +
                        ") t on a.ID = t.ACCOUNT_ID")
                .getResultList();

        resultList.stream()
                .map(Arrays::toString)
                .forEach(System.out::println);

        Account account = em.find(Account.class, 1L);
        System.out.println(account);


//        SelectQuery mainSelect = new SelectQuery()
//                .addCustomColumns("app.ACC_NUMBER")
//                .addCustomFromTable("app.ACCOUNT")
//                .addCondition(new CustomCondition("accountId = '12345' "))
//                .validate();
//
//        System.out.println(mainSelect.toString());

    }


    private void createTestData() {
        final EntityManager entityManager = factory.createEntityManager();

        try {
            EntityTransaction transaction = entityManager.getTransaction();

            transaction.begin();

            Date date = new Date();

            Account acc1 = new Account("1000-100200-100");
            acc1.addTransaction(Arrays.asList(
                    new Transaction(BigDecimal.valueOf(10.01), date, Currency.USD),
                    new Transaction(BigDecimal.valueOf(10.02), date, Currency.USD),
                    new Transaction(BigDecimal.valueOf(10.03), date, Currency.EUR),
                    new Transaction(BigDecimal.valueOf(10.04), date, Currency.USD)
            ));

            entityManager.persist(acc1);
//
//            List<Transaction> acc2Txns = Arrays.asList(
//                    new Transaction(BigDecimal.valueOf(20.01), date, Currency.USD),
//                    new Transaction(BigDecimal.valueOf(20.02), date, Currency.USD),
//                    new Transaction(BigDecimal.valueOf(20.03), date, Currency.EUR),
//                    new Transaction(BigDecimal.valueOf(20.04), date, Currency.USD)
//            );
//
//            Account acc2 = new Account("2000-200200-200");
//            acc2.setTransactions(acc2Txns);
//
//            entityManager.persist(acc2);

            transaction.commit();

        } finally {
            entityManager.close();
        }

    }

}

