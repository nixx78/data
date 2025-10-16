package lv.nixx.poc.poc.repository;

import lv.nixx.poc.orm.AccountType;
import lv.nixx.poc.orm.TransactionType;
import lv.nixx.poc.poc.BaseTest;
import lv.nixx.poc.repository.AccountTypeRepository;
import lv.nixx.poc.repository.TransactionTypeRepository;
import lv.nixx.poc.repository.auditable.Auditable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuditableRepositoryTest extends BaseTest {

    @Autowired
    TransactionTypeRepository transactionTypeRepository;

    @Autowired
    AccountTypeRepository accountTypeRepository;

    @Test
    void addAndModifyTypes() {

        Auditable savedType = transactionTypeRepository.saveWithAuditable(new TransactionType("NEW_TXN_TYPE"));
        Auditable savedAccountType = accountTypeRepository.saveWithAuditable(new AccountType("NEW_ACCOUNT_TYPE"));

        assertAll(
                () -> assertNotNull(savedType.getUpdatedAt()),
                () -> assertNotNull(savedAccountType.getUpdatedBy())
        );

    }


}
