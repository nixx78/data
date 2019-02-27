package lv.nixx.poc.spring.data.repository.txn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import lv.nixx.poc.spring.data.domain.txn.Currency;
import lv.nixx.poc.spring.data.domain.txn.Transaction;
import lv.nixx.poc.spring.data.domain.txn.TransactionDTO;
import lv.nixx.poc.spring.data.domain.txn.TransactionProjection;
import lv.nixx.poc.spring.data.repository.txn.CurrencyRepository;
import lv.nixx.sping.jdbc.GenericJdbcTest;

public class TransactionDBTest extends GenericJdbcTest {

	@Autowired
	private CurrencyRepository currencyRepo;

	@Autowired
	private TransactionRepository txnRepo;

	private final LocalDateTime now = LocalDateTime.now();

	@Test
	public void crudOperations() {

		Iterable<Currency> allCurrencies = currencyRepo.findAll();
		System.out.println(allCurrencies);
		
		System.out.println(txnRepo.findAll());
		
		long existingCount = txnRepo.count();

		txnRepo.save(new Transaction(now, BigDecimal.valueOf(10.01), "descr1", USD, "acc1"));
		Transaction txn2 = txnRepo.save(new Transaction(now, BigDecimal.valueOf(20.01), "descr2", EUR, "acc2"));

		assertEquals(existingCount + 2, txnRepo.count());

		Transaction txn = txnRepo.findById(txn2.getId()).orElse(null);
		assertEquals("descr2", txn.getDescription());

		assertFalse(txnRepo.existsById(-1L));

		txnRepo.deleteById(txn2.getId());
		assertFalse(txnRepo.existsById(txn2.getId()));

	}

	@Test
	public void requestsTest() {
		createInitialData();

		System.out.println("====================");

		Collection<Transaction> txns = txnRepo.findAllByAccountOrderByAmount("account2");
		txns.forEach(System.out::println);

		System.out.println("====================");
		txns = txnRepo.findAllByCurrencyAlphaCode(EUR.getAlphaCode());

		txns.forEach(t -> System.out.println("Txn by alphaCode: " + t));

	}

	@Test
	public void pagingSample() throws InterruptedException {

		final int txnCount = 20;

		for (int i = 0; i < txnCount; i++) {
			txnRepo.save(new Transaction(now, BigDecimal.valueOf(i), "descr" + i, USD, "acc1"));
		}

		Page<Transaction> page = null;
		int p = 0;
		do {
			page = txnRepo.findAll(PageRequest.of(p, 8));

			List<Transaction> content = page.getContent();
			System.out.println("Page: " + p + " count: " + page.getNumberOfElements() + " : " + content);
			p++;

		} while (page.hasNext());
	}

	@Test
	public void findTopFirstSample() {
		createInitialData();

		Collection<Transaction> result = txnRepo.findTop3ByOrderByAmountDesc();
		result.forEach(System.out::println);

		assertEquals(3, result.size());

		System.out.println("==============================================");
		result = txnRepo.findFirst3ByCurrencyAlphaCode(USD.getAlphaCode(), Sort.by("Amount").descending());
		result.forEach(System.out::println);
	}

	@Test
	@Transactional(readOnly = true)
	public void getAllStreamed() {
		createInitialData();

		try (Stream<Transaction> all = txnRepo.streamAllPaged(Pageable.unpaged())) {
			all.forEach(System.out::println);
		}

	}

	@Test
	public void projectionAndDTOSample() {

		createInitialData();

		Collection<TransactionProjection> all = txnRepo.findAllByAccount("account4");

		for (TransactionProjection t : all) {
			System.out.println(t.getId() + ":" + t.getDescription() + ":" + t.getFormattedAmount());
		}

		Collection<TransactionDTO> txns = txnRepo.findAllDtoedByCurrency(USD);
		txns.forEach(System.out::println);
		System.out.println("====================");

		assertEquals(6, txns.size());

		Collection<TransactionDTO> notExisting = txnRepo.findAllDtoedByCurrency(new Currency("RUB", 978));
		System.out.println(notExisting);
		assertTrue(notExisting.isEmpty());

		Collection<Transaction> findAllAsTransaction = txnRepo.findAllBy(Transaction.class);
		Collection<TransactionDTO> findAllAsDTO = txnRepo.findAllBy(TransactionDTO.class);

		assertFalse(findAllAsTransaction.isEmpty());
		assertFalse(findAllAsDTO.isEmpty());
	}

	@Test
	public void queryByExampleSample() {
		createInitialData();

		Transaction txnKey = new Transaction();
		txnKey.setCurrency(USD);
		txnKey.setDescription("descr3");

		Example<Transaction> example = Example.of(txnKey);
		Optional<Transaction> result = txnRepo.findOne(example);
		assertTrue(result.isPresent());

		txnKey = new Transaction();
		txnKey.setDescription("descr3");

		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("description", match -> match.startsWith());

		example = Example.of(txnKey, matcher);

		result = txnRepo.findOne(example);
		assertTrue(result.isPresent());

	}

}
