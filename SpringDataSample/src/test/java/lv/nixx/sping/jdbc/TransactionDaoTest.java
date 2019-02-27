package lv.nixx.sping.jdbc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import lv.nixx.poc.spring.jdbc.TransactionDTO;
import lv.nixx.poc.spring.jdbc.TransactionDao;


public class TransactionDaoTest extends GenericJdbcTest {

	@Autowired
	private TransactionDao dao;

	@Test
	public void getAllTransactionsTest() {
		createInitialData();

		Collection<TransactionDTO> txns = dao.getAllTransactions();

		txns.forEach(System.out::println);

		assertNotNull(txns);
		assertFalse(txns.isEmpty());
	}

}
