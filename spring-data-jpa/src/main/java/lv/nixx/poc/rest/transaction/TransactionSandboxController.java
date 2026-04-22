package lv.nixx.poc.rest.transaction;

import lombok.RequiredArgsConstructor;
import lv.nixx.poc.model.SaveResult;
import lv.nixx.poc.service.transaction.ServiceWithTransactionSynchronization;
import lv.nixx.poc.service.transaction.TransactionUsingEntityManagerSandbox;
import lv.nixx.poc.service.transaction.TransactionUsingTemplate;
import lv.nixx.poc.service.transaction.TransactionWithRepoSandbox;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sandbox/transaction")
public class TransactionSandboxController {

    private final TransactionWithRepoSandbox transactionWithRepoSandbox;
    private final TransactionUsingEntityManagerSandbox transactionWithEntityManager;
    private final TransactionUsingTemplate transactionUsingTemplate;
    private final ServiceWithTransactionSynchronization serviceWithTransactionSynchronization;

    @PostMapping("/addUsingTransactional")
    public SaveResult addAccountUsingTransactional(
            @RequestParam String accountName,
            @RequestParam Long customerId
    ) {
        return transactionWithRepoSandbox.createNewAccountInTransaction(accountName, customerId);
    }

    @PostMapping("/addWithoutTransaction")
    public SaveResult addWithoutTransaction(
            @RequestParam String accountName,
            @RequestParam Long customerId
    ) {
        return transactionWithRepoSandbox.createNewAccount(accountName, customerId);
    }

    @PostMapping("/addUsingEntityManager")
    public SaveResult addAccountUsingEntityManager(
            @RequestParam String accountName,
            @RequestParam Long customerId
    ) {
        return transactionWithEntityManager.createNewAccountUsingEntityManager(accountName, customerId);
    }

    @PostMapping("/addUsingTemplate")
    public SaveResult addUsingTemplate(
            @RequestParam String accountName,
            @RequestParam Long customerId
    ) {
        return transactionUsingTemplate.createNewAccountUsingTemplate(accountName, customerId);
    }

    @PostMapping("/addUsingTransactionSynchronization")
    public SaveResult addUsingTransactionSynchronization(
            @RequestParam String accountName,
            @RequestParam Long customerId
    ) {
        return serviceWithTransactionSynchronization.createNewAccountInTransaction(accountName, customerId);
    }


}
