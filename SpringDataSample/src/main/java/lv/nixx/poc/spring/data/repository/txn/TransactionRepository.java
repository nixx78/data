package lv.nixx.poc.spring.data.repository.txn;

import org.springframework.data.repository.CrudRepository;

import lv.nixx.poc.spring.data.domain.txn.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
