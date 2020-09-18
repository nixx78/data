package lv.nixx.poc.txs.rest;

import lv.nixx.poc.txs.data.BalanceRepository;
import lv.nixx.poc.txs.data.TransactionRepository;
import lv.nixx.poc.txs.data.model.AccountBalance;
import lv.nixx.poc.txs.data.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
public class DataController {

    @Autowired
    private TransactionRepository txnRepo;

    @Autowired
    private BalanceRepository balanceRepository;

    @GetMapping("/clearAll")
    public void clearAll() {
        txnRepo.deleteAll();
        balanceRepository.deleteAll();
    }

    @GetMapping("/tablesContent")
    public Map<String, Object> getTablesContent() {
        return Map.of(
                "balance", balanceRepository.findAll(),
                "transaction", txnRepo.findAll()
        );
    }

    @GetMapping("/transaction")
    public List<Transaction> getAllTransactions() {
        return txnRepo.findAll();
    }

    @GetMapping("/balance")
    public List<AccountBalance> getAllBalance() {
        return balanceRepository.findAll();
    }


    @PostMapping("/transaction")
    public Transaction add(@RequestBody Transaction txn) {
        return txnRepo.save(txn);
    }

    @GetMapping("/collection/{ids}")
    public void test(@PathVariable Collection<Long> ids) {
        System.out.println(ids.size());
    }


}
