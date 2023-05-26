package lv.nixx.data.mysql.alpha;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class GetTransaction implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(GetTransaction.class, args);
    }

    @Override
    public void run(String... args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Txn> txns = jdbcTemplate.query("select * from transaction", (rs, rowNum) -> new Txn()
                .setId(rs.getLong("id"))
                .setAmount(rs.getBigDecimal("amount"))
                .setCurrency(rs.getString("currency"))
        );
        System.out.println(txns);
        System.out.println(" ============ ALPHA ===================");
    }

    @Data
    @Accessors(chain = true)
    static class Txn {
        private Long id;
        private BigDecimal amount;
        private String currency;
    }


}
