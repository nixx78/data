package lv.nixx.poc.db.jpa.query;

import lv.nixx.poc.db.domain.txn.TransactionWithIdClass;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TxnQuerySample {

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    @BeforeAll
    void init() {
        createData();
    }

    @Test
    void groupByQuerySampleTest() {

        EntityManager em = factory.createEntityManager();

        List<TransactionWithIdClass> resultList = em.createQuery("select t from TransactionWithIdClass t", TransactionWithIdClass.class).getResultList();

        System.out.println(resultList);

        List resultList1 = em.createNativeQuery("SELECT t.key1, t.key2, t.key3, t.data, t.amount FROM TXN_ID_CLASS_TABLE t" +
                        " INNER JOIN (" +
                        "    SELECT type, MAX(amount) max_amount" +
                        "    FROM TXN_ID_CLASS_TABLE" +
                        "    GROUP BY type" +
                        ") b ON t.type = b.type AND t.amount = b.max_amount")
                .getResultList();

        List resultList2 = em.createNativeQuery("SELECT a.key1, a.key2, a.key3, a.data, a.amount, a.type FROM TXN_ID_CLASS_TABLE a" +
                        " LEFT OUTER JOIN TXN_ID_CLASS_TABLE b ON a.type = b.type AND a.amount < b.amount" +
                        " WHERE b.type IS NULL")
                .getResultList();

        System.out.println(resultList2);
    }

    @Test
    void querySampleByCriteria() {

        Session session = (Session) factory.createEntityManager();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TransactionWithIdClass> cr = cb.createQuery(TransactionWithIdClass.class);
        Root<TransactionWithIdClass> root = cr.from(TransactionWithIdClass.class);
        cr.select(root).where(cb.greaterThanOrEqualTo(root.get("amount"), 50));

        Query<TransactionWithIdClass> query = session.createQuery(cr);
        List<TransactionWithIdClass> results = query.getResultList();

        assertEquals(3, results.size());
    }

    private void createData() {
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
    }


}
