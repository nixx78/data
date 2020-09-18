package lv.nixx.poc.txs;

import lv.nixx.poc.txs.data.BalanceRepository;
import lv.nixx.poc.txs.data.TransactionRepository;
import lv.nixx.poc.txs.data.model.AccountBalance;
import lv.nixx.poc.txs.data.model.Container;
import lv.nixx.poc.txs.data.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BalancePersistence {

    @Autowired
    private TransactionRepository txnRepo;

    @Autowired
    private BalanceRepository balanceRepository;

    // Transaction is ok, everything is working
    @Transactional
    public Container saveTxnAndBalanceTransactionalAnnotated(Container c) {
        return saveWithoutTransaction(c);
    }

    public Container saveWithoutTransaction(Container c) {
        final AccountBalance balance = c.getBalance();

        final List<Transaction> savedTxn = txnRepo.saveAll(c.getTxn());
        final AccountBalance savedBalance = balanceRepository.save(balance);

        if (balance.getAccountId().equalsIgnoreCase("Error")) {
            // Transaction rollback in this case
            throw new IllegalArgumentException("Wrong account");
        }

        return new Container(savedTxn, savedBalance);
    }

    public Container saveCallingInternalMethod(Container c) {
        return saveTxnAndBalanceTransactionalAnnotated(c);
    }
}
