package lv.nixx.poc.db.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "TXN_TABLE", schema = "app")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "txnId")
public class Transaction {

    @Id
    @GeneratedValue
    @Column(name = "TXN_ID")
    private Long txnId;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", updatable = false, insertable = false)
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

    public String toString() {
        Account acc = this.getAccount();
        return "Transaction(txnId=" + this.getTxnId() + ", account=" + (acc == null ? null : acc.getNumber()) + ", amount=" + this.getAmount() + ", date=" + this.getDate() + ", currency=" + this.getCurrency() + ")";
    }

}
