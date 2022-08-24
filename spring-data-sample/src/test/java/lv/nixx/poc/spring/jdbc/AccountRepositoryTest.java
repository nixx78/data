package lv.nixx.poc.spring.jdbc;

import lv.nixx.poc.spring.jdbc.model.Account;
import lv.nixx.poc.spring.jdbc.model.Transaction;
import lv.nixx.poc.spring.jdbc.repository.AccountRepository;
import lv.nixx.poc.spring.jdbc.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(AccountRepositoryTest.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void cleanup() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void crudWithEntitySample() {

        String accountId = "accountId.1";

        Transaction txn1 = new Transaction()
                .setCurrency("EUR")
                .setDate(LocalDateTime.parse("2022-05-09T10:00:23.77"))
                .setDescription("Simple transaction 1")
                .setAccountId(accountId)
                .setAmount(BigDecimal.valueOf(10.00));

        Transaction txn2 = new Transaction()
                .setCurrency("EUR")
                .setDate(LocalDateTime.parse("2022-05-09T10:00:23.77"))
                .setDescription("Simple transaction 1")
                .setAccountId(accountId)
                .setAmount(BigDecimal.valueOf(11.00));

        Transaction txn3 = new Transaction()
                .setCurrency("USD")
                .setDate(LocalDateTime.parse("2022-05-09T10:00:23.77"))
                .setDescription("Another transaction")
                .setAccountId(accountId)
                .setAmount(BigDecimal.valueOf(12.00));


        Account savedAccount = accountRepository.save(new Account()
                .setAccountId(accountId)
                .setNew(true)
                .setTransactions(List.of(txn1, txn2, txn3))
                .setName("Account1"));

        Account account = accountRepository.findById(savedAccount.getAccountId()).orElse(null);
        assertNotNull(account);

        List<Transaction> savedTransactions = StreamSupport.stream(transactionRepository.findAll().spliterator(), false).toList();

        assertAll(
                () -> assertEquals("Account1", account.getName()),
                () -> assertEquals(3, account.getTransactions().size()),
                () -> assertEquals(3, savedTransactions.size())
        );
    }

}
