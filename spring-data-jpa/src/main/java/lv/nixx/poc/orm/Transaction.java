package lv.nixx.poc.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION_TBL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
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

    @Column(name = "sStatus")
    private String status;

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
