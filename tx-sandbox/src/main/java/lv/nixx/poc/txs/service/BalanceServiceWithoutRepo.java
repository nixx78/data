package lv.nixx.poc.txs.service;

import lv.nixx.poc.txs.model.Container;
import lv.nixx.poc.txs.orm.AccountBalance;
import lv.nixx.poc.txs.orm.Transaction;
import lv.nixx.poc.txs.repo.BalanceRepository;
import lv.nixx.poc.txs.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BalanceServiceWithoutRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Container saveTxnAndBalanceTransactionalAnnotated(Container c) {
        return saveWithoutTransaction(c);
    }

    public Container saveWithoutTransaction(Container c) {
        final AccountBalance balance = c.getBalance();

        Collection<Transaction> txn = c.getTxn();

        final List<Transaction> savedTxn = new ArrayList<>(txn.size());
        for(Transaction t: txn) {
            entityManager.persist(t);
            entityManager.flush();
            entityManager.clear();

            savedTxn.add(t);
        }

        final AccountBalance savedBalance = entityManager.merge(balance);

        if (balance.getAccountId().equalsIgnoreCase("Error")) {
            // Transaction rollback in this case
            throw new IllegalArgumentException("Wrong account");
        }

        return new Container(savedTxn, savedBalance);
    }

}
