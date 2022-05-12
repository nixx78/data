package lv.nixx.poc.spring.jdbc.repository;

import lv.nixx.poc.spring.jdbc.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepositoryWithPaging extends PagingAndSortingRepository<Transaction, Long> {
}
