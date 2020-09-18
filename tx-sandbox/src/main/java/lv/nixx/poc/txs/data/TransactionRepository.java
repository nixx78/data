package lv.nixx.poc.txs.data;

import lv.nixx.poc.txs.data.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
