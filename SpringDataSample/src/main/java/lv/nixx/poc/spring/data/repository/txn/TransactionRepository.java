package lv.nixx.poc.spring.data.repository.txn;

import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;

import lv.nixx.poc.spring.data.domain.txn.Currency;
import lv.nixx.poc.spring.data.domain.txn.Transaction;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
	
	public Collection<Transaction> findAllByCurrency(Currency currency);
	public Collection<Transaction> findAllByAccountOrderByAmount(String account);
	public Collection<Transaction> findAllByCurrencyAlphaCode(String alphaCode);

}
