package lv.nixx.poc.db.jpa;

import lv.nixx.poc.db.TablePrinter;
import lv.nixx.poc.db.domain.txn.EmbeddedTransactionId;
import lv.nixx.poc.db.domain.txn.TransactionId;
import lv.nixx.poc.db.domain.txn.TransactionWithEmbeddedId;
import lv.nixx.poc.db.domain.txn.TransactionWithIdClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertNotNull;

public class TransactionPersistTest {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    private TablePrinter txnPrinter = new TablePrinter("TXN_TABLE");
    private TablePrinter txnIdTablePrinter = new TablePrinter("TXN_ID_CLASS_TABLE");

    @Test
    public void embeddableIdTest() throws Exception {

        TransactionWithEmbeddedId txn1 = new TransactionWithEmbeddedId();
        txn1.setTransactionId(new EmbeddedTransactionId("key1", 10L, false));
        txn1.setData("Data1");

        TransactionWithEmbeddedId txn2 = new TransactionWithEmbeddedId();
        txn2.setTransactionId(new EmbeddedTransactionId("key2", 20L, true));
        txn2.setData("Data2");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        em.persist(txn1);
        em.persist(txn2);

        em.getTransaction().commit();

        System.out.println("========================");
        txnPrinter.print();
        System.out.println("========================");

        TransactionWithEmbeddedId actualTxn = em.find(TransactionWithEmbeddedId.class, new EmbeddedTransactionId("key2", 20L, true));
        assertNotNull(actualTxn);
    }

    @Test
    public void idClassSampleTest() throws Exception {

        TransactionWithIdClass txn1 = new TransactionWithIdClass();
        txn1.setKey1("Key1");
        txn1.setKey2(100L);
        txn1.setKey3(false);
        txn1.setData("Data1");

        TransactionWithIdClass txn2 = new TransactionWithIdClass();
        txn2.setKey1("Key2");
        txn2.setKey2(200L);
        txn2.setKey3(true);
        txn2.setData("Data2");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        em.persist(txn1);
        em.persist(txn2);

        em.getTransaction().commit();

        System.out.println("========================");
        txnIdTablePrinter.print();
        System.out.println("========================");

        TransactionWithIdClass actualTxn = em.find(TransactionWithIdClass.class, new TransactionId("Key2", 200L, true));
        assertNotNull(actualTxn);
    }



}
