package lv.nixx.poc.poc;

import lv.nixx.poc.orm.Account;
import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.orm.Transaction;
import lv.nixx.poc.orm.TransactionType;
import lv.nixx.poc.repository.AccountRepository;
import lv.nixx.poc.repository.CustomerRepository;
import lv.nixx.poc.repository.TransactionRepository;
import lv.nixx.poc.repository.TypeRepository;
import org.junit.jupiter.api.Test;
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

@SpringBootTest
class JPASandbox {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void test() {

        Customer cust1 = customerRepository.save(new Customer("Name1", "Surname1", LocalDate.parse("1978-06-12")));

        Map<String, Account> byAccountName = accountRepository.saveAll(List.of(
                        new Account("account1", cust1.getId()),
                        new Account("account2", cust1.getId())
                ))
                .stream()
                .collect(toMap(Account::getName, Function.identity()));


        Map<String, TransactionType> typeByName = typeRepository.saveAll(List.of(
                        new TransactionType("type1"),
                        new TransactionType("type2")
                ))
                .stream()
                .collect(toMap(TransactionType::getTypeName, Function.identity()));

        transactionRepository.saveAll(
                List.of(
                        Transaction.builder()
                                .amount(BigDecimal.valueOf(1.00))
                                .timestamp(LocalDateTime.parse("2024-06-13T12:00:01"))
                                .account(byAccountName.get("account1"))
                                .type(typeByName.get("type1"))
                                .build(),
                        Transaction.builder()
                                .amount(BigDecimal.valueOf(2.00))
                                .timestamp(LocalDateTime.parse("2024-06-13T12:00:00"))
                                .account(byAccountName.get("account1"))
                                .type(typeByName.get("type2"))
                                .build(),
                        Transaction.builder()
                                .amount(BigDecimal.valueOf(2.00))
                                .timestamp(LocalDateTime.parse("2024-06-13T12:00:00"))
                                .account(byAccountName.get("account2"))
                                .type(typeByName.get("type1"))
                                .build()
                ));

        System.out.println(transactionRepository.findAll());

        Collection<Transaction> latestTransaction = transactionRepository.getLatestTransaction();
        latestTransaction.forEach(System.out::println);

        System.out.println("----------------------");

        Collection<Customer> allCustomers = customerRepository.findAll();
        allCustomers.forEach(System.out::println);
    }


}
