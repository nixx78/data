package lv.nixx.poc.db.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "TXN_TABLE", schema = "app")
@NoArgsConstructor
@EqualsAndHashCode(of = "txnId")
public class Transaction {

    @Id
    @GeneratedValue
    @Column(name = "TXN_ID")
    private Long txnId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Column(name = "AMOUNT", length = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "DATE")
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY")
    private Currency currency;

    public Transaction(BigDecimal amount, Date date, Currency currency) {
        this.amount = amount;
        this.date = date;
        this.currency = currency;
    }


}
