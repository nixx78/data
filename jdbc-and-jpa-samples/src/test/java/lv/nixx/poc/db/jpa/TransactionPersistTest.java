package lv.nixx.poc.db.jpa;

import lv.nixx.poc.db.TablePrinter;
import lv.nixx.poc.db.domain.txn.TransactionId;
import lv.nixx.poc.db.domain.txn.TransactionWithIdClass;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionPersistTest {

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    private final TablePrinter txnIdTablePrinter = new TablePrinter("TXN_ID_CLASS_TABLE");

    @Test
    public void idClassSampleTest() {

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
