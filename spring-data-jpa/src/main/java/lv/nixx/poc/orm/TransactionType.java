package lv.nixx.poc.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.nixx.poc.repository.auditable.Auditable;

import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION_TYPE_TBL")
@Data
@NoArgsConstructor
public class TransactionType implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "sTypeName")
    private String typeName;

    @Column(name = "sUser")
    private String user;

    @Column(name = "dtTimestamp")
    private LocalDateTime timestamp;

    public TransactionType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public void setUpdatedBy(String user) {
        this.user = user;
    }

    @Override
    public String getUpdatedBy() {
        return user;
    }

    @Override
    public void setUpdatedAt(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return timestamp;
    }
}
