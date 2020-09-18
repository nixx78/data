package lv.nixx.poc.txs.rest;

import lv.nixx.poc.txs.TransactionSandboxService;
import lv.nixx.poc.txs.data.AppException;
import lv.nixx.poc.txs.data.model.Container;
import lv.nixx.poc.txs.data.model.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @Autowired
    private TransactionSandboxService transactionSandboxService;

    @PostMapping("/bulk")
    public Statistic saveAllInTransaction(@RequestBody Container c) {
        try {
            return transactionSandboxService.saveAllInTransaction(c);
        } catch (AppException ae) {
            final Statistic stat = ae.getStat();
            return stat;
        }
    }


    @PostMapping("/bulkSaveInInternalMethod")
    public Statistic saveAllInTransactionInternalMethod(@RequestBody Container c) {
        return transactionSandboxService.saveInTxnInternalMethodCall(c);
    }


    @PostMapping("/bulkUsingTransactionTemplate")
    public Statistic saveUsingTransactionTemplate(@RequestBody Container c) {
        return transactionSandboxService.saveUsingTransactionTemplate(c);
    }


}
