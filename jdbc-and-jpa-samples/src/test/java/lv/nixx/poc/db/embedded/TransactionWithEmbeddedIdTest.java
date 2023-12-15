package lv.nixx.poc.db.embedded;

import lv.nixx.poc.db.TablePrinter;
import lv.nixx.poc.db.domain.embedded.EmbeddedTransactionId;
import lv.nixx.poc.db.domain.embedded.TransactionWithEmbeddedId;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionWithEmbeddedIdTest {

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    private final TablePrinter txnPrinter = new TablePrinter("TXN_TABLE");

    @Test
    public void embeddableIdTest() {

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        em.persist(TransactionWithEmbeddedId.builder()
                .transactionId(new EmbeddedTransactionId("key1", 10L, false))
                .data("Data1")
                .build());

        em.persist(TransactionWithEmbeddedId.builder()
                .transactionId(new EmbeddedTransactionId("key2", 20L, true))
                .data("Data2")
                .build());

        em.getTransaction().commit();

        txnPrinter.print();

        TransactionWithEmbeddedId actualTxn = em.find(TransactionWithEmbeddedId.class, new EmbeddedTransactionId("key2", 20L, true));
        assertNotNull(actualTxn);
    }

}
