package lv.nixx.poc.poc.repository;

import lv.nixx.poc.orm.AccountType;
import lv.nixx.poc.orm.TransactionType;
import lv.nixx.poc.poc.BaseTest;
import lv.nixx.poc.repository.AccountTypeRepository;
import lv.nixx.poc.repository.TransactionTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserAwareRepositoryTest extends BaseTest {

    @Autowired
    TransactionTypeRepository transactionTypeRepository;

    @Autowired
    AccountTypeRepository accountTypeRepository;

    @Test
    void addAndModifyTypes() {

        TransactionType savedType = transactionTypeRepository.saveWithUser(new TransactionType("NEW_TXN_TYPE"));
        AccountType savedAccountType = accountTypeRepository.saveWithUser(new AccountType("NEW_ACCOUNT_TYPE"));

        assertAll(
                () -> assertNotNull(savedType.getUser()),
                () -> assertNotNull(savedAccountType.getUser())
        );

    }


}
