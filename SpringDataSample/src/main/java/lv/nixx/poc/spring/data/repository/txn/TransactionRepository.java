package lv.nixx.poc.spring.data.repository.txn;

import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import lv.nixx.poc.spring.data.domain.txn.Currency;
import lv.nixx.poc.spring.data.domain.txn.Transaction;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
	
	public Collection<Transaction> findAllByCurrency(Currency currency);
	public Collection<Transaction> findAllByAccountOrderByAmount(String account);
	public Collection<Transaction> findAllByCurrencyAlphaCode(String alphaCode);
	public Collection<Transaction> findTop3ByOrderByAmountDesc();
	public Collection<Transaction> findFirst3ByCurrencyAlphaCode(String alphaCode, Sort sort);
	
	@Query("select t from Transaction t")
	public Stream<Transaction> streamAllPaged(Pageable pageable);
	
}
