package lv.nixx.poc.service.transaction;

import jakarta.transaction.Transactional;
import lv.nixx.poc.model.SaveResult;
import lv.nixx.poc.orm.Account;
import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.orm.Transaction;
import lv.nixx.poc.repository.AccountRepository;
import lv.nixx.poc.repository.CustomerRepository;
import lv.nixx.poc.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionWithRepoSandbox {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public TransactionWithRepoSandbox(AccountRepository accountRepository,
                                      CustomerRepository customerRepository,
                                      TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public SaveResult createNewAccountInTransaction(String accountName, Long customerId) {
        return createNewAccount(accountName, customerId);
    }

    public SaveResult createNewAccount(String accountName, Long customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer, id [%s] doesn't exists".formatted(customerId)));
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

}
