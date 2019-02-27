package lv.nixx.sping.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import lv.nixx.poc.spring.jdbc.TransactionDTO;

public class SqlQuerySample extends GenericJdbcTest {

	@Test
	public void sqlQueryTest() {

		TransactionMappingQuery query = new TransactionMappingQuery(dataSource);
		List<TransactionDTO> txn = query.execute("account4");

		txn.forEach(System.out::println);
	}

	class TransactionMappingQuery extends MappingSqlQuery<TransactionDTO> {

		public TransactionMappingQuery(DataSource ds) {
			super(ds,"select t.*, c.* from TRANSACTIONS t, CURRENCY c where t.currency_code=c.alpha_code and account = ?");
			super.declareParameter(new SqlParameter("account", Types.CHAR));
			compile();
		}

		@Override
		protected TransactionDTO mapRow(ResultSet rs, int rowNumber) throws SQLException {
			long id = rs.getLong("ID");
			String descr = rs.getString("descr");
			BigDecimal amount = rs.getBigDecimal("amount");
			String currencyCode = rs.getString("currency_code");
			String numericCode = rs.getString("numeric_code");

			return new TransactionDTO(id, currencyCode, numericCode, amount, descr);
		}

	}

}
