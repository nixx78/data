package lv.nixx.poc.spring.jdbc;

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

        Transaction createdTxn = transactionRepository.save(
                new Transaction()
                        .setCurrency("USD")
                        .setDate(LocalDateTime.parse("2022-05-09T10:00:23.77"))
                        .setDescription("Simple transaction")
                        .setAccountId("accountId")
                        .setAmount(BigDecimal.valueOf(10.00))
        );

        log.info("Created txn: {}", createdTxn);

        Optional<Transaction> byId = transactionRepository.findById(createdTxn.getId());

        log.info("Find by id [{}]", byId.orElse(null));

        Collection<Transaction> actualList = StreamSupport
                .stream(transactionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        log.info("All Transaction using Repository: {}", actualList);

        Collection<Transaction> allTransactions = transactionDao.getAllTransactions();

        log.info("All Transaction using DAO: {}", allTransactions);

        assertAll(
                () -> assertNotNull(byId),
                () -> assertEquals(1, actualList.size()),
                () -> assertEquals(1, allTransactions.size())
        );

        Transaction anotherTxn = transactionRepository.save(
                new Transaction()
                        .setCurrency("EUR")
                        .setDate(LocalDateTime.parse("2022-05-09T10:00:23.77"))
                        .setDescription("Another transaction")
                        .setAccountId("accountId")
                        .setAmount(BigDecimal.valueOf(20.00))
        );

        assertEquals(2, transactionDao.getAllTransactions().size());

        Long idToDelete = anotherTxn.getId();
        transactionRepository.deleteAllById(List.of(idToDelete));

        assertAll(
                () -> assertEquals(1, transactionRepository.count()),
                () -> assertFalse(transactionRepository.existsById(idToDelete))
        );


    }

}
