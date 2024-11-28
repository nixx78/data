package lv.nixx.poc.rest.transaction;

import lv.nixx.poc.model.SaveResult;
import lv.nixx.poc.service.transaction.TransactionUsingEntityManagerSandbox;
import lv.nixx.poc.service.transaction.TransactionUsingTemplate;
import lv.nixx.poc.service.transaction.TransactionWithRepoSandbox;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionSandboxController {

    private final TransactionWithRepoSandbox transactionWithRepoSandbox;
    private final TransactionUsingEntityManagerSandbox transactionWithEntityManager;
    private final TransactionUsingTemplate transactionUsingTemplate;

    public TransactionSandboxController(TransactionWithRepoSandbox transactionWithRepoSandbox,
                                        TransactionUsingEntityManagerSandbox transactionWithEntityManager,
                                        TransactionUsingTemplate transactionUsingTemplate
    ) {
        this.transactionWithRepoSandbox = transactionWithRepoSandbox;
        this.transactionWithEntityManager = transactionWithEntityManager;
        this.transactionUsingTemplate = transactionUsingTemplate;
    }

    @PostMapping("/sandbox/transaction/addUsingTransactional")
    public SaveResult addAccountUsingTransactional(
            @RequestParam String accountName,
            @RequestParam Long customerId
    ) {
        return transactionWithRepoSandbox.createNewAccountInTransaction(accountName, customerId);
    }

    @PostMapping("/sandbox/transaction/addWithoutTransaction")
    public SaveResult addWithoutTransaction(
            @RequestParam String accountName,
            @RequestParam Long customerId
    ) {
        return transactionWithRepoSandbox.createNewAccount(accountName, customerId);
    }

    @PostMapping("/sandbox/transaction/addUsingEntityManager")
    public SaveResult addAccountUsingEntityManager(
            @RequestParam String accountName,
            @RequestParam Long customerId
    ) {
        return transactionWithEntityManager.createNewAccountUsingEntityManager(accountName, customerId);
    }

    @PostMapping("/sandbox/transaction/addUsingTemplate")
    public SaveResult addUsingTemplate(
            @RequestParam String accountName,
            @RequestParam Long customerId
    ) {
        return transactionUsingTemplate.createNewAccountUsingTemplate(accountName, customerId);
    }

}
