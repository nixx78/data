package lv.nixx.poc.spring.jdbc;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@SpringBootTest
class TransactionDaoTest {

    private static final Logger log = LoggerFactory.getLogger(TransactionDaoTest.class);

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void saveAndGetTest() {

        TransactionDTO createdTxn = transactionRepository.save(
                new TransactionDTO()
                        .setCurrency("USD")
                        .setDate(LocalDateTime.parse("2022-05-09T10:00:23.77"))
                        .setDescription("Simple transaction")
                        .setAccountId("accountId")
                        .setAmount(BigDecimal.valueOf(10.00))
        );

        log.info("Created txn: {}", createdTxn);

        Collection<TransactionDTO> allTransactions = transactionDao.getAllTransactions();
        System.out.println(allTransactions);

    }
}
