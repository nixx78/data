package lv.nixx.poc.spring.data.repository.txn;


import org.springframework.data.repository.PagingAndSortingRepository;

import lv.nixx.poc.spring.data.domain.txn.Transaction;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

}
