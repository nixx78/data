package lv.nixx.poc.spring.jdbc;

import lv.nixx.poc.spring.jdbc.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Service
public class TransactionDao {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionDao(JdbcTemplate jdbcTemplate, TransactionRepository transactionRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionRepository = transactionRepository;
    }

    public Transaction save(Transaction txn) {
        return transactionRepository.save(txn);
    }

    public Collection<Transaction> getAllTransactions() {
        return jdbcTemplate.query("select * from TRANSACTION_TBL", new TransactionMapper());
    }

    static class TransactionMapper implements RowMapper<Transaction> {

        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("ID");
            String currency = rs.getString("currency");
            String accountId = rs.getString("account_id");
            BigDecimal amount = rs.getBigDecimal("amount");
            String descr = rs.getString("descr");

            return new Transaction()
                    .setId(id)
                    .setCurrency(currency)
                    .setAccountId(accountId)
                    .setAmount(amount)
                    .setDescription(descr);
        }

    }


}
