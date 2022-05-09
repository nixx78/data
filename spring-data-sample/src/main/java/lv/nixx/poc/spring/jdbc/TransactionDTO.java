package lv.nixx.poc.spring.jdbc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Accessors(chain = true)

@Table("TRANSACTION_TBL")
public class TransactionDTO {

	@Id
	@Column("ID")
	private long id;

	@Column("ACCOUNT_ID")
	private String accountId;

	@Column("CURRENCY")
	private String currency;

	@Column("AMOUNT")
	private BigDecimal amount;

	@Column("DESCR")
	private String description;

	@Column("DATE")
	private LocalDateTime date;
}
