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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceWithTransactionalEventListener {

    private static final Logger log = LoggerFactory.getLogger(ServiceWithTransactionalEventListener.class);

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

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

        applicationEventPublisher.publishEvent(new CommitEvent(account.getId()));

        return new SaveResult(account.getId(), account.getName(), savedTransaction.stream().map(Transaction::getId).toList());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCommitEvent(CommitEvent commitEvent) {
        log.info("Handle commit event: {}", commitEvent);
    }

    public record CommitEvent(Long accountId) {
    }

}
