package lv.nixx.samples.json.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private LocalDateTime dateTime;

    public Transaction(int id, int pos, BigDecimal amount, Type type, Currency currency) {
        this.id = id;
        this.pos = pos;
        this.amount = amount;
        this.type = type;
        this.currency = currency;
    }

    public Transaction(int id, int pos, BigDecimal amount, Type type, Currency currency, LocalDateTime dateTime) {
        this(id, pos, amount, type, currency);
        this.dateTime = dateTime;
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
