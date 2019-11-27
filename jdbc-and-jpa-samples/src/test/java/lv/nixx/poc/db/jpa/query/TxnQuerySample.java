package lv.nixx.poc.db.jpa.query;

import lv.nixx.poc.db.domain.txn.TransactionWithIdClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class TxnQuerySample {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    @Test
    public void groupByQuerySampleTest() {

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        em.persist(new TransactionWithIdClass()
                .setKey1("Key1")
                .setKey2(100L)
                .setKey3(false)
                .setData("Data1")
                .setAmount(BigDecimal.valueOf(100.00))
                .setType("Type1")
        );

        em.persist(new TransactionWithIdClass()
                .setKey1("Key1")
                .setKey2(101L)
                .setKey3(false)
                .setData("Data1")
                .setAmount(BigDecimal.valueOf(100.00))
                .setType("Type1")
        );

        em.persist(new TransactionWithIdClass()
                .setKey1("Key1")
                .setKey2(200L)
                .setKey3(false)
                .setData("Data1")
                .setAmount(BigDecimal.valueOf(50.00))
                .setType("Type1")
        );

        em.persist(new TransactionWithIdClass()
                .setKey1("Key3")
                .setKey2(200L)
                .setKey3(true)
                .setData("Data3")
                .setAmount(BigDecimal.valueOf(30.00))
                .setType("Type2")
        );

        em.getTransaction().commit();

        List<TransactionWithIdClass> resultList = em.createQuery("select t from TransactionWithIdClass t", TransactionWithIdClass.class).getResultList();

        System.out.println(resultList);

        List resultList1 = em.createNativeQuery("SELECT t.key1, t.key2, t.key3, t.data, t.amount FROM TXN_ID_CLASS_TABLE t" +
                " INNER JOIN (" +
                "    SELECT type, MAX(amount) max_amount" +
                "    FROM TXN_ID_CLASS_TABLE" +
                "    GROUP BY type" +
                ") b ON t.type = b.type AND t.amount = b.max_amount")
                .getResultList();

//        SELECT a.*
//        FROM TableA a
//        WHERE ID < (SELECT MAX(ID) FROM TableA b WHERE a.Value=b.Value GROUP BY Value HAVING COUNT(*) > 1

        List resultList2 = em.createNativeQuery("SELECT a.key1, a.key2, a.key3, a.data, a.amount, a.type FROM TXN_ID_CLASS_TABLE a" +
                " LEFT OUTER JOIN TXN_ID_CLASS_TABLE b ON a.type = b.type AND a.amount < b.amount" +
                " WHERE b.type IS NULL")
                .getResultList();

//        List resultList3 = em.createNativeQuery(
//                "SELECT x.* FROM " +
//                "(SELECT a.key1, a.key2, a.key3, a.data, a.amount, a.type FROM TXN_ID_CLASS_TABLE a" +
//                " LEFT OUTER JOIN TXN_ID_CLASS_TABLE b ON a.type = b.type AND a.amount < b.amount" +
//                " WHERE b.type IS NULL" +
//                ") x WHERE x.type < (SELECT MAX(TYPE) FROM TXN_ID_CLASS_TABLE GROUP BY type) ")
//                .getResultList();

        System.out.println(resultList2);


    }

}
