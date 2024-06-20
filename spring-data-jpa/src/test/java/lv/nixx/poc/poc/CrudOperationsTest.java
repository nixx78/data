package lv.nixx.poc.poc;

import lv.nixx.poc.orm.Account;
import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.orm.Transaction;
import lv.nixx.poc.poc.util.TypesLoader;
import lv.nixx.poc.repository.AccountRepository;
import lv.nixx.poc.repository.CustomerRepository;
import lv.nixx.poc.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CrudOperationsTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TypesLoader typesLoader;
    private Customer customer;

    @BeforeAll()
    void prepareInitialData() {
        typesLoader.loadTypes();
        this.customer = customerRepository.save(new Customer("Name2", "Surname2", LocalDate.parse("1978-07-12")));
    }

    @BeforeEach
    void cleanup() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void saveAndUpdateLinkedFields() {

        accountRepository.saveAll(List.of(
                new Account("account1", customer, typesLoader.getSavingAccount()),
                new Account("account2", customer, typesLoader.getCurrentAccount()),
                new Account("accountToRemove", customer, typesLoader.getCurrentAccount())
        ));

        Customer customer = customerRepository.findById(this.customer.getId()).orElse(null);

        Map<String, Account> existingAccountsByName = customer.getAccounts()
                .stream()
                .collect(toMap(Account::getName, Function.identity()));

        Account updatedAccount = existingAccountsByName.get("account2");
        updatedAccount.setName("account2.updated");

        customer.setAccounts(Set.of(
                existingAccountsByName.get("account1"),
                updatedAccount,
                new Account("newAccount", this.customer, typesLoader.getSavingAccount())
        ));

        System.out.println("----- Before save ------------");
        customerRepository.save(customer);
        System.out.println("----- After save ------------");

        Customer updatedCustomer = customerRepository.findById(this.customer.getId()).orElseThrow(IllegalArgumentException::new);
        Collection<String> updatedAccounts = updatedCustomer
                .getAccounts()
                .stream()
                .map(Account::getName)
                .toList();

        assertThat(updatedAccounts)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(List.of("account1", "account2.updated", "newAccount"));
    }

    @Test
    void modifyTransactionState() {

        Account account = accountRepository.save(new Account("account1", customer, typesLoader.getSavingAccount()));

        Long id = transactionRepository.save(
                Transaction.builder()
                        .amount(BigDecimal.valueOf(1.00))
                        .timestamp(LocalDateTime.parse("2024-06-13T12:00:01"))
                        .account(account)
                        .type(typesLoader.getType1())
                        .status("ExistingStatus")
                        .build()
        ).getId();

        transactionRepository.updateTransactionStatus(id, "ExistingStatus.Updated");

        assertEquals("ExistingStatus.Updated", transactionRepository.findById(id).orElse(null).getStatus());
    }

}
