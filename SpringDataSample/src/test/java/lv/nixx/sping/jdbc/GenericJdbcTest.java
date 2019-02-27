package lv.nixx.sping.jdbc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lv.nixx.poc.spring.data.TransactionDBConfig;
import lv.nixx.poc.spring.data.domain.txn.Currency;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TransactionDBConfig.class)
@SuppressWarnings("serial")
public abstract class GenericJdbcTest {

	protected final Currency USD = new Currency("USD", 810);
	protected final Currency EUR = new Currency("EUR", 978);
	
	@Autowired
	@Qualifier("transactionDB")
	protected DataSource dataSource;

	@Before
	public final void createInitialData() {
	
		// Clear tables
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("delete from TRANSACTIONS");
		jdbcTemplate.execute("delete from CURRENCY");
	
		// Insert currencies
		SimpleJdbcInsert insertCurrency = new SimpleJdbcInsert(dataSource).withTableName("CURRENCY");
	
		insertCurrency.execute(new HashMap<String, Object>() {
			{
				put("alpha_code", USD.getAlphaCode());
				put("numeric_code", USD.getNumeric());
			}
		});
	
		insertCurrency.execute(new HashMap<String, Object>() {
			{
				put("alpha_code", EUR.getAlphaCode());
				put("numeric_code", EUR.getNumeric());
			}
		});
	
		// Insert transactions
		final LocalDateTime now = LocalDateTime.now();
	
		SimpleJdbcInsert insertTransaction = new SimpleJdbcInsert(dataSource).withTableName("TRANSACTIONS");
	
		AtomicInteger index = new AtomicInteger();
	
		Arrays.asList(new HashMap<String, Object>() {
			{
				put("id", index.getAndIncrement());
				put("date", Timestamp.valueOf(now));
				put("amount", BigDecimal.valueOf(10.01));
				put("descr", "descr1");
				put("account", "account1");
				put("currency_code", USD.getAlphaCode());
	
			}
		}, new HashMap<String, Object>() {
			{
				put("id", index.getAndIncrement());
				put("date", Timestamp.valueOf(now));
				put("amount", BigDecimal.valueOf(20.05));
				put("descr", "descr2");
				put("account", "account2");
				put("currency_code", EUR.getAlphaCode());
			}
		}, new HashMap<String, Object>() {
			{
				put("id", index.getAndIncrement());
				put("date", Timestamp.valueOf(now));
				put("amount", BigDecimal.valueOf(10.03));
				put("descr", "descr3");
				put("account", "account2");
				put("currency_code", USD.getAlphaCode());
			}
		}, new HashMap<String, Object>() {
			{
				put("id", index.getAndIncrement());
				put("date", Timestamp.valueOf(now));
				put("amount", BigDecimal.valueOf(77.77));
				put("descr", "descr41");
				put("account", "account4");
				put("currency_code", USD.getAlphaCode());
			}
		}, new HashMap<String, Object>() {
			{
				put("id", index.getAndIncrement());
				put("date", Timestamp.valueOf(now));
				put("amount", BigDecimal.valueOf(4.2));
				put("descr", "descr42");
				put("account", "account4");
				put("currency_code", USD.getAlphaCode());
			}
		}, new HashMap<String, Object>() {
			{
				put("id", index.getAndIncrement());
				put("date", Timestamp.valueOf(now));
				put("amount", BigDecimal.valueOf(1.0));
				put("descr", "descr43");
				put("account", "account4");
				put("currency_code", USD.getAlphaCode());
			}
		}, new HashMap<String, Object>() {
			{
				put("id", index.getAndIncrement());
				put("date", Timestamp.valueOf(now));
				put("amount", BigDecimal.valueOf(8.0));
				put("descr", "pref_descr43");
				put("account", "account4");
				put("currency_code", USD.getAlphaCode());
			}
		}).forEach(t -> {
			insertTransaction.execute(t);
		});
		

	}

}