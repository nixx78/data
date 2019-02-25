package lv.nixx.poc.spring.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

// https://docs.spring.io/spring/docs/4.0.x/spring-framework-reference/html/jdbc.html

@Service
public class TransactionDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("transactionDB")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Collection<TransactionDTO> getAllTransactions() {
        return jdbcTemplate.query("select t.*, c.* from TRANSACTIONS t, CURRENCY c where t.currency_code=c.alpha_code", new TransactionMapper());
	}
	
	class TransactionMapper implements RowMapper<TransactionDTO>{

		@Override
		public TransactionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			long id = rs.getLong("ID");
			String descr = rs.getString("descr");
			BigDecimal amount = rs.getBigDecimal("amount");
			String currencyCode = rs.getString("currency_code");
			String numericCode = rs.getString("numeric_code");
			

			return new TransactionDTO(id, currencyCode, numericCode, amount, descr);
		}
		
	}

	
}
