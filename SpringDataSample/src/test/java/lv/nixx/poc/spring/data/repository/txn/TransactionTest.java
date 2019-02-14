package lv.nixx.poc.spring.data.repository.txn;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lv.nixx.poc.spring.data.JPAConfiguration;
import lv.nixx.poc.spring.data.domain.txn.Currency;
import lv.nixx.poc.spring.data.domain.txn.Transaction;
import lv.nixx.poc.spring.data.repository.txn.CurrencyRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPAConfiguration.class)
public class TransactionTest {
	
	@Autowired
	private CurrencyRepository currencyRepo;
	
	@Autowired
	private TransactionRepository txnRepo;
	
	@Before
	public void init() {
		txnRepo.deleteAll();
		currencyRepo.deleteAll();
		
		currencyRepo.save(new Currency("USD", 810));
		currencyRepo.save(new Currency("EUR", 978));
	}
	
	@Test
	public void simpleSample() {
		
		Currency usd = new Currency("USD", 810);
		Currency eur = new Currency("EUR", 810);

		Iterable<Currency> allCurrencies = currencyRepo.findAll();
		System.out.println(allCurrencies);
		
		LocalDateTime now = LocalDateTime.now();
		
		txnRepo.save(new Transaction(now, BigDecimal.valueOf(10.01), "descr1", usd));
		txnRepo.save(new Transaction(now, BigDecimal.valueOf(20.01), "descr2", eur));
	}
	

}
