package lv.nixx.poc.spring.data.repository.txn;

import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import lv.nixx.poc.spring.data.domain.txn.Currency;
import lv.nixx.poc.spring.data.domain.txn.Transaction;
import lv.nixx.poc.spring.data.domain.dto.TransactionDTO;
import lv.nixx.poc.spring.data.domain.txn.TransactionProjection;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long>, QueryByExampleExecutor<Transaction> {

	// Query with DTO as Result
	Collection<TransactionDTO> findAllDtoedByCurrency(Currency currency);

	Collection<Transaction> findAllByAccountOrderByAmount(String account);

	Collection<Transaction> findAllByCurrencyAlphaCode(String alphaCode);

	Collection<Transaction> findTop3ByOrderByAmountDesc();

	Collection<Transaction> findFirst3ByCurrencyAlphaCode(String alphaCode, Sort sort);

	@Query("select t from Transaction t")
	Stream<Transaction> streamAllPaged(Pageable pageable);

	// Query with projection (Interface) as result
	Collection<TransactionProjection> findAllByAccount(String account);

	<T> Collection<T> findAllBy(Class<T> type);

}
