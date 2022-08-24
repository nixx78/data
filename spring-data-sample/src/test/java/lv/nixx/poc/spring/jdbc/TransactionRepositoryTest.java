package lv.nixx.poc.spring.jdbc;

import lv.nixx.poc.spring.jdbc.model.Transaction;
import lv.nixx.poc.spring.jdbc.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(TransactionRepositoryTest.class);

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void cleanup() {
        transactionRepository.deleteAll();
    }

    @Test
    void crudWithEntitySample() {

        transactionRepository.save(
                new Transaction()
                        .setCurrency("EUR")
                        .setDate(LocalDateTime.parse("2022-05-09T10:00:23.77"))
                        .setDescription("Simple transaction 1")
                        .setAccountId("accountId")
                        .setAmount(BigDecimal.valueOf(10.00))
        );

        transactionRepository.save(
                new Transaction()
                        .setCurrency("EUR")
                        .setDate(LocalDateTime.parse("2022-05-09T10:00:23.77"))
                        .setDescription("Simple transaction 1")
                        .setAccountId("AnotherAccountId")
                        .setAmount(BigDecimal.valueOf(10.00))
        );

        Transaction txn2 = transactionRepository.save(
                new Transaction()
                        .setCurrency("USD")
                        .setDate(LocalDateTime.parse("2022-05-09T10:00:23.77"))
                        .setDescription("Another transaction")
                        .setAccountId("AnotherAccountId")
                        .setAmount(BigDecimal.valueOf(10.00))
        );

        Optional<Transaction> byId = transactionRepository.findById(txn2.getId());

        assertAll(
                () -> assertEquals(txn2, byId.orElse(null)),
                () -> assertEquals(2, transactionRepository.getByCurrency("EUR").size()),
                () -> assertEquals(1, transactionRepository.getByAccountId("accountId").size())
        );

        Collection<Transaction> actualList = StreamSupport
                .stream(transactionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        log.info("All Transaction using Repository: {}", actualList);

        Collection<Transaction> allTransactions = transactionDao.getAllTransactions();

        assertAll(
                () -> assertNotNull(byId),
                () -> assertEquals(3, actualList.size()),
                () -> assertEquals(3, allTransactions.size())
        );

        Long anotherTxnId = txn2.getId();

        txn2.setDescription("Updated description");
        transactionRepository.save(txn2);

        Transaction updatedTransaction = transactionRepository.findById(anotherTxnId).orElse(null);
        assertEquals("Updated description", updatedTransaction.getDescription());

        transactionRepository.deleteAllById(List.of(anotherTxnId));
        assertFalse(transactionRepository.existsById(anotherTxnId));
    }

}
