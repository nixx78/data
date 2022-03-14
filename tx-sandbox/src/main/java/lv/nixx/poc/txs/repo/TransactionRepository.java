package lv.nixx.poc.txs.repo;

import lv.nixx.poc.txs.orm.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
