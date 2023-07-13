package lv.nixx.poc.txs;

import lv.nixx.poc.txs.model.Container;
import lv.nixx.poc.txs.orm.AccountBalance;
import lv.nixx.poc.txs.orm.Transaction;
import lv.nixx.poc.txs.repo.BalanceRepository;
import lv.nixx.poc.txs.repo.TransactionRepository;
import lv.nixx.poc.txs.service.BalanceServiceWithoutRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(DBCleanupExtension.class)
class BalanceServiceWithoutRepoTest {

    @Autowired
    private BalanceServiceWithoutRepo service;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    private final Date timestamp = new Date();
    private final List<Transaction> txn = List.of(
            new Transaction()
                    .setAccountId("AccountID1")
                    .setDate(timestamp)
                    .setAmount(BigDecimal.valueOf(10))
                    .setDescription("Description1"),
            new Transaction()
                    .setAccountId("AccountID1")
                    .setDate(timestamp)
                    .setAmount(BigDecimal.valueOf(20))
                    .setDescription("Description2")
    );

    @Test
    void saveUsingTransactionSuccessTest() {

        Container c = new Container(txn, new AccountBalance()
                .setAccountId("AccountID1")
                .setBalance(BigDecimal.valueOf(100.00))
                .setTimestamp(timestamp)
                .setUpdateUser("user")
        );

        Container container = service.saveTxnAndBalanceTransactionalAnnotated(c);
        assertNotNull(container);

        assertAll(
                () -> assertEquals(1, balanceRepository.findAll().size()),
                () -> assertEquals(2, transactionRepository.findAll().size())
        );
    }

    @Test
    void saveUsingTransactionFailTest() {

        Date timestamp = new Date();

        Container c = new Container(txn, new AccountBalance()
                .setAccountId("Error")
                .setBalance(BigDecimal.valueOf(100.00))
                .setTimestamp(timestamp)
                .setUpdateUser("user")
        );

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.saveTxnAndBalanceTransactionalAnnotated(c)
        );
        assertEquals("Wrong account", ex.getMessage());

        assertAll(
                () -> assertEquals(0, balanceRepository.findAll().size()),
                () -> assertEquals(0, transactionRepository.findAll().size())
        );
    }

    @Test
    @Transactional
    void saveWithoutTransactionFailTest() {

        Date timestamp = new Date();

        Container c = new Container(txn, new AccountBalance()
                .setAccountId("Error")
                .setBalance(BigDecimal.valueOf(100.00))
                .setTimestamp(timestamp)
                .setUpdateUser("user")
        );

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.saveWithoutTransaction(c)
        );
        assertEquals("Wrong account", ex.getMessage());

        assertAll(
                () -> assertEquals(0, balanceRepository.findAll().size()),
                () -> assertEquals(2, transactionRepository.findAll().size())
        );
    }


}
