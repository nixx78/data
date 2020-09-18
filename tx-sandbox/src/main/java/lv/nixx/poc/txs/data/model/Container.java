package lv.nixx.poc.txs.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class Container {

    private Collection<Transaction> txn;
    private AccountBalance balance;

}
