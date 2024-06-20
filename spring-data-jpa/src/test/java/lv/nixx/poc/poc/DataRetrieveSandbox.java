package lv.nixx.poc.poc;

import lv.nixx.poc.orm.Account;
import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.orm.Transaction;
import lv.nixx.poc.poc.util.TypesLoader;
import lv.nixx.poc.repository.AccountRepository;
import lv.nixx.poc.repository.CustomerRepository;
import lv.nixx.poc.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataRetrieveSandbox {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer1;

    @Autowired
    private TypesLoader typesLoader;

    @BeforeAll()
    void prepareInitialData() {

        typesLoader.loadTypes();

        this.customer1 = customerRepository.save(new Customer("Name1", "Surname1", LocalDate.parse("1978-06-12")));
        customerRepository.save(new Customer("Name2", "Surname2", LocalDate.parse("1978-07-12")));

        Map<String, Account> byAccountName = accountRepository.saveAll(List.of(
                        new Account("account1", customer1, typesLoader.getCurrentAccount()),
                        new Account("account2", customer1, typesLoader.getCardAccount()),
                        new Account("account3", customer1, typesLoader.getSavingAccount())
                ))
                .stream()
                .collect(toMap(Account::getName, Function.identity()));

        transactionRepository.saveAll(
                List.of(
                        Transaction.builder()
                                .amount(BigDecimal.valueOf(1.00))
                                .timestamp(LocalDateTime.parse("2024-06-13T12:00:01"))
                                .account(byAccountName.get("account1"))
                                .type(typesLoader.getType1())
                                .build(),
                        Transaction.builder()
                                .amount(BigDecimal.valueOf(2.00))
                                .timestamp(LocalDateTime.parse("2024-06-13T12:00:00"))
                                .account(byAccountName.get("account1"))
                                .type(typesLoader.getType2())
                                .build(),
                        Transaction.builder()
                                .amount(BigDecimal.valueOf(3.00))
                                .timestamp(LocalDateTime.parse("2024-06-13T12:00:00"))
                                .account(byAccountName.get("account2"))
                                .type(typesLoader.getType1())
                                .build()
                ));
    }

    @Test
    void findAllCustomers() {
        System.out.println("----Find all ------------------");
        Customer c = customerRepository.findById(customer1.getId()).orElse(null);

        assertNotNull(c);
    }

    @Test
    void findAllCustomersUsingEntityGraph() {
        System.out.println("---- @EntityGraph ------------------");
        Customer c = customerRepository.findUsingEntityGraph(customer1.getId());

        assertNotNull(c);
    }

    @Test
    void findCustomerWithAccounts_JOIN_FETCH() {
        System.out.println("--- JOIN FETCH ----------------");
        Customer c = customerRepository.findCustomerWithAccounts(1L);

        assertNotNull(c);
    }

    @Test
    void getLatestTransactionsTest() {
        Collection<Transaction> latestTransaction = transactionRepository.getLatestTransactionForEachCustomerAccount(customer1.getId());

        assertEquals(2, latestTransaction.size());
    }


}
