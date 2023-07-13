package lv.nixx.poc.txs.rest;

import lv.nixx.poc.txs.orm.AccountBalance;
import lv.nixx.poc.txs.orm.Transaction;
import lv.nixx.poc.txs.repo.BalanceRepository;
import lv.nixx.poc.txs.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
public class DataController {

    private final TransactionRepository txnRepo;
    private final BalanceRepository balanceRepository;

    @Autowired
    public DataController(TransactionRepository txnRepo, BalanceRepository balanceRepository) {
        this.txnRepo = txnRepo;
        this.balanceRepository = balanceRepository;
    }

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

    @PostMapping("/transactions")
    public Collection<Transaction> add(@RequestBody Collection<Transaction> txns) {
        return txnRepo.saveAll(txns);
    }

    @GetMapping("/collection/{ids}")
    public void test(@PathVariable Collection<Long> ids) {
        System.out.println(ids.size());
    }


}
