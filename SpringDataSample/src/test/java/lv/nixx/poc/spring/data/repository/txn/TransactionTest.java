package lv.nixx.poc.spring.data.repository.txn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	public void crudOperations() {
		
		final Currency usd = new Currency("USD", 810);
		final Currency eur = new Currency("EUR", 978);

		Iterable<Currency> allCurrencies = currencyRepo.findAll();
		System.out.println(allCurrencies);
		
		LocalDateTime now = LocalDateTime.now();
		
		txnRepo.save(new Transaction(now, BigDecimal.valueOf(10.01), "descr1", usd));
		Transaction txn2 = txnRepo.save(new Transaction(now, BigDecimal.valueOf(20.01), "descr2", eur));
		
		assertEquals(2, txnRepo.count());
		
		Transaction txn = txnRepo.findById(txn2.getId()).orElse(null);
		assertEquals("descr2", txn.getDescription());
		
		assertFalse(txnRepo.existsById(-1L));
		
		txnRepo.deleteById(txn2.getId());
		assertFalse(txnRepo.existsById(txn2.getId()));
	
	}
	
	@Test
	public void requestSamples() {
		// TODO Make sample from there... https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/#mapping.object-creation.details
	}
	
	@Test
	public void pagingSample() throws InterruptedException {
		
		final Currency usd = new Currency("USD", 810);
		final int txnCount = 20;
		final LocalDateTime now = LocalDateTime.now();

		for (int i = 0; i < txnCount; i++) {
			txnRepo.save(new Transaction(now, BigDecimal.valueOf(i), "descr" + i, usd));
		}
		
		assertEquals(20, txnRepo.count());
		
		Page<Transaction> page = null;
		int p = 0;
		do {
			page = txnRepo.findAll(PageRequest.of(p, 8));
			
			List<Transaction> content = page.getContent();
			System.out.println("Page: " + p + " count: " + page.getNumberOfElements() + " : " + content);
			p++;
			
		} while (page.hasNext());
	
		
	}
	

}
