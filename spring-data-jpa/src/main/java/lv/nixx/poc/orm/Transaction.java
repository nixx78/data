package lv.nixx.poc.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION_TBL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "dAmount")
    private BigDecimal amount;

    @Column(name = "dtTimestamp")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false, updatable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "typeId", referencedColumnName = "id")
    private TransactionType type;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", account=" + account == null ? null : account.getName() +
                ", type=" + type +
                '}';
    }
}
