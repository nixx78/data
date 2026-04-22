package lv.nixx.poc.service.transaction;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lv.nixx.poc.model.SaveResult;
import lv.nixx.poc.orm.Account;
import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.orm.Transaction;
import lv.nixx.poc.repository.AccountRepository;
import lv.nixx.poc.repository.CustomerRepository;
import lv.nixx.poc.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceWithTransactionSynchronization {

    private static final Logger log = LoggerFactory.getLogger(ServiceWithTransactionSynchronization.class);

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public SaveResult createNewAccountInTransaction(String accountName, Long customerId) {
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


        if (TransactionSynchronizationManager.isSynchronizationActive()) {

            // Call flow: beforeCommit → beforeCompletion → COMMIT → afterCommit → afterCompletion
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {

                        @Override
                        public void beforeCommit(boolean readOnly) {
                            log.info("TransactionSynchronization:Before commit");
                        }

                        @Override
                        public void beforeCompletion() {
                            log.info("TransactionSynchronization:BeforeCompletion");
                        }

                        @Override
                        public void afterCommit() {
                            log.info("TransactionSynchronization:Transaction committed!");
                        }

                        @Override
                        public void afterCompletion(int status) {
                            log.info("TransactionSynchronization:Completed with status {}", +status);
                        }

                        @Override
                        public void flush() {
                            log.info("TransactionSynchronization:Flush");
                        }
                    });
        }

        return new SaveResult(account.getId(), account.getName(), savedTransaction.stream().map(Transaction::getId).toList());
    }
}
