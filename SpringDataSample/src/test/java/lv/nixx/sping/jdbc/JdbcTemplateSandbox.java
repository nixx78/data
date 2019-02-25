package lv.nixx.sping.jdbc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lv.nixx.poc.spring.data.TransactionDBConfig;
import lv.nixx.poc.spring.data.domain.txn.Currency;
import lv.nixx.poc.spring.data.domain.txn.Transaction;
import lv.nixx.poc.spring.data.repository.txn.CurrencyRepository;
import lv.nixx.poc.spring.data.repository.txn.TransactionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TransactionDBConfig.class)
//TODO  Create common class for tests
public class JdbcTemplateSandbox {

	private static final Currency USD = new Currency("USD", 810);
	private static final Currency EUR = new Currency("EUR", 978);

	@Autowired
	@Qualifier("transactionDB")
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	// Needed only to create test data
	@Autowired
	private TransactionRepository txnRepo;

	@Autowired
	private CurrencyRepository currencyRepo;

	@Before
	public void init() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		txnRepo.deleteAll();
		currencyRepo.deleteAll();

		currencyRepo.save(USD);
		currencyRepo.save(EUR);

		createInitialData();
	}

	@Test
	public void queryForObjectSample() {
		Long cnt = jdbcTemplate.queryForObject("select count(*) from TRANSACTIONS", Long.class);
		System.out.println("Transactions count 'All':" + cnt);
	}

	@Test
	public void namedParameterJdbcTemplateSample() {
		String sql = "select count(*) from TRANSACTIONS where currency_code = :currency_code";
		Map<String, String> namedParameters = Collections.singletonMap("currency_code", "USD");
		Integer cnt = this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);

		System.out.println("Transactions count 'USD':" + cnt);
	}
	
	@Test
	public void queryForListSample() {
		List<Map<String, Object>> lst = jdbcTemplate.queryForList("select * from Transactions");

		// 1 row - 1 map
		lst.forEach(System.out::println);
	}
	
	//TODO 13.4 JDBC batch operations


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
