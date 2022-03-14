package lv.nixx.poc.txs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lv.nixx.poc.txs.orm.AccountBalance;
import lv.nixx.poc.txs.orm.Transaction;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class Container {

    private Collection<Transaction> txn;
    private AccountBalance balance;

}
