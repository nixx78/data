package lv.nixx.poc.spring.data.domain.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lv.nixx.poc.spring.data.domain.txn.Currency;

@Data
@RequiredArgsConstructor
public class TransactionDTO {

	private final long id;

	private final Currency currency;

	private final BigDecimal amount;

	private final String description;

}
