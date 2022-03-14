package lv.nixx.poc.txs.service;

import lv.nixx.poc.txs.repo.BalanceRepository;
import lv.nixx.poc.txs.repo.TransactionRepository;
import lv.nixx.poc.txs.orm.AccountBalance;
import lv.nixx.poc.txs.model.Container;
import lv.nixx.poc.txs.orm.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BalanceServiceWithRepo {

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
