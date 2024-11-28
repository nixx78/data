package lv.nixx.poc.service.transaction;

import lv.nixx.poc.model.SaveResult;
import lv.nixx.poc.orm.Account;
import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.orm.Transaction;
import lv.nixx.poc.repository.AccountRepository;
import lv.nixx.poc.repository.CustomerRepository;
import lv.nixx.poc.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionUsingTemplate {

    private final PlatformTransactionManager transactionManager;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public TransactionUsingTemplate(PlatformTransactionManager transactionManager,
                                    AccountRepository accountRepository,
                                    CustomerRepository customerRepository,
                                    TransactionRepository transactionRepository
    ) {
        this.transactionManager = transactionManager;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    public SaveResult createNewAccountUsingTemplate(String accountName, Long customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer, id [%s] doesn't exists".formatted(customerId)));

        return new TransactionTemplate(transactionManager).execute(
                t -> {
                    Account account = accountRepository.save(new Account(accountName, customer, null));

                    if (accountName.contains("ERROR")) {
                        throw new IllegalStateException("Internal error after Account save");
                    }

                    List<Transaction> savedTransaction = transactionRepository.saveAll(List.of(
                            new Transaction()
                                    .setAccount(account)
                                    .setStatus("OK")
                                    .setAmount(BigDecimal.valueOf(10.00)),
                            new Transaction()
                                    .setAccount(account)
                                    .setStatus("OK")
                                    .setAmount(BigDecimal.valueOf(10.01))
                    ));

                    return new SaveResult(account.getId(), account.getName(), savedTransaction.stream().map(Transaction::getId).toList());
                }

        );
    }

}
