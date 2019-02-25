package lv.nixx.sping.jdbc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lv.nixx.poc.spring.data.TransactionDBConfig;
import lv.nixx.poc.spring.data.domain.txn.Currency;
import lv.nixx.poc.spring.data.domain.txn.Transaction;
import lv.nixx.poc.spring.data.domain.txn.TransactionDTO;
import lv.nixx.poc.spring.data.repository.txn.CurrencyRepository;
import lv.nixx.poc.spring.data.repository.txn.TransactionRepository;
import lv.nixx.poc.spring.jdbc.TransactionDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TransactionDBConfig.class)
public class TransactionDaoTest {

	@Autowired
	private TransactionDao dao;

	// Needed only to create test data
	@Autowired
	private TransactionRepository txnRepo;

	@Autowired
	private CurrencyRepository currencyRepo;

	private static final Currency USD = new Currency("USD", 810);
	private static final Currency EUR = new Currency("EUR", 978);

	@Before
	public void init() {
		txnRepo.deleteAll();
		currencyRepo.deleteAll();

		currencyRepo.save(USD);
		currencyRepo.save(EUR);
	}

	@Test
	public void getAllTransactionsTest() {
		createInitialData();

		Collection<TransactionDTO> txns = dao.getAllTransactions();

		txns.forEach(System.out::println);

		assertNotNull(txns);
		assertFalse(txns.isEmpty());
	}

	private void createInitialData() {
		final LocalDateTime now = LocalDateTime.now();

		Arrays.asList(new Transaction(now, BigDecimal.valueOf(10.01), "descr1", USD, "account1"),
				new Transaction(now, BigDecimal.valueOf(20.05), "descr2", EUR, "account2"),
				new Transaction(now, BigDecimal.valueOf(10.03), "descr3", USD, "account2"),
				new Transaction(now, BigDecimal.valueOf(77.77), "descr41", USD, "account4"),
				new Transaction(now, BigDecimal.valueOf(4.2), "descr42", USD, "account4"),
				new Transaction(now, BigDecimal.valueOf(1.0), "descr43", USD, "account4"),
				new Transaction(now, BigDecimal.valueOf(8.0), "pref_descr43", USD, "account4")).forEach(txnRepo::save);

	}

}
