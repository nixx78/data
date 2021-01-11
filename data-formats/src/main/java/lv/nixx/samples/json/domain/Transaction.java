package lv.nixx.samples.json.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {

	private int id;
	private int pos;
	private BigDecimal amount;

	@JsonSetter(nulls = Nulls.SKIP)
	private Type type = Type.CREDIT;

	@JsonProperty("Txn description")
	private String description;

	private Currency currency;

	public Transaction(int id, int pos, BigDecimal amount, Type type, Currency currency) {
		this.id = id;
		this.pos = pos;
		this.amount = amount;
		this.type = type;
		this.currency = currency;
	}

	@JsonProperty("taxFee")
	public double getTax() {
		return amount.multiply(BigDecimal.valueOf(0.21)).doubleValue();
	}

	@JsonIgnore
	public double getIgnoredField() {
		return 0.00;
	}

}
