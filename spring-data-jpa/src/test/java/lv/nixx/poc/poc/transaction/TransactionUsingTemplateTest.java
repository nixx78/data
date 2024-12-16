package lv.nixx.poc.poc.transaction;

import lv.nixx.poc.model.SaveResult;
import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.poc.BaseTest;
import lv.nixx.poc.repository.AccountRepository;
import lv.nixx.poc.repository.CustomerRepository;
import lv.nixx.poc.service.transaction.TransactionUsingTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionUsingTemplateTest extends BaseTest {

    @Autowired
    TransactionUsingTemplate service;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Test
    void successTest() {
        Customer customer = customerRepository.save(new Customer("name", "surname", null));
        SaveResult newAccount = service.createNewAccountUsingTemplate("newAccount", customer.getId());

        assertNotNull(newAccount.accountId());
    }

    @Test
    void failTest() {
        Customer customer = customerRepository.save(new Customer("name", "surname", null));
        assertThrows(IllegalStateException.class, ()->  service.createNewAccountUsingTemplate("ERROR", customer.getId()));

        assertEquals(0, accountRepository.findAll().size());
    }

}
