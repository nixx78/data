package lv.nixx.poc.spring.jdbc;

import java.math.BigDecimal;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TransactionDTO {

	private final long id;

	private final String alphaCode;
	private final String numericCode;

	private final BigDecimal amount;

	private final String description;

}
