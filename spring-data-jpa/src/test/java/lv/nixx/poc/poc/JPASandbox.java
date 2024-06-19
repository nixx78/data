package lv.nixx.poc.poc;

import lv.nixx.poc.orm.*;
import lv.nixx.poc.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JPASandbox {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;
    private Customer customer1;

    @BeforeAll()
    void prepareInitialData() {
        Map<String, TransactionType> txnTypeByName = typeRepository.saveAll(List.of(
                        new TransactionType("type1"),
                        new TransactionType("type2")
                ))
                .stream()
                .collect(toMap(TransactionType::getTypeName, Function.identity()));

        Map<String, AccountType> accountTypeByName = accountTypeRepository.saveAll(List.of(
                        new AccountType("CurrentAccount"),
                        new AccountType("SavingAccount"),
                        new AccountType("CardAccount")
                )).stream()
                .collect(toMap(AccountType::getName, Function.identity()));


        this.customer1 = customerRepository.save(new Customer("Name1", "Surname1", LocalDate.parse("1978-06-12")));
        customerRepository.save(new Customer("Name2", "Surname2", LocalDate.parse("1978-07-12")));

        Map<String, Account> byAccountName = accountRepository.saveAll(List.of(
                        new Account("account1", customer1, accountTypeByName.get("CurrentAccount")),
                        new Account("account2", customer1, accountTypeByName.get("CardAccount")),
                        new Account("account3", customer1, accountTypeByName.get("SavingAccount"))
                ))
                .stream()
                .collect(toMap(Account::getName, Function.identity()));

        transactionRepository.saveAll(
                List.of(
                        Transaction.builder()
                                .amount(BigDecimal.valueOf(1.00))
                                .timestamp(LocalDateTime.parse("2024-06-13T12:00:01"))
                                .account(byAccountName.get("account1"))
                                .type(txnTypeByName.get("type1"))
                                .build(),
                        Transaction.builder()
                                .amount(BigDecimal.valueOf(2.00))
                                .timestamp(LocalDateTime.parse("2024-06-13T12:00:00"))
                                .account(byAccountName.get("account1"))
                                .type(txnTypeByName.get("type2"))
                                .build(),
                        Transaction.builder()
                                .amount(BigDecimal.valueOf(3.00))
                                .timestamp(LocalDateTime.parse("2024-06-13T12:00:00"))
                                .account(byAccountName.get("account2"))
                                .type(txnTypeByName.get("type1"))
                                .build()
                ));
    }

    @Test
    void findAllCustomers() {
        System.out.println("----Find all ------------------");
        Customer c = customerRepository.findById(customer1.getId()).orElse(null);

        System.out.println("-");

    }

    @Test
    void findAllCustomersUsingEntityGraph() {
        System.out.println("---- @EntityGraph ------------------");
        Customer allUsingEntityGraph = customerRepository.findUsingEntityGraph(customer1.getId());

        System.out.println("-");
    }

    @Test
    void findCustomerWithAccounts_JOIN_FETCH() {
        System.out.println("--- JOIN FETCH ----------------");
        Customer customersWithAccounts = customerRepository.findCustomerWithAccounts(1L);

        System.out.println("-");
    }

    @Test
    void getLatestTransactionsTest() {
        Collection<Transaction> latestTransaction = transactionRepository.getLatestTransactionForEachCustomerAccount(customer1.getId());

        assertEquals(2, latestTransaction.size());
    }


}
