package lv.nixx.poc.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.nixx.poc.repository.auditable.Auditable;

import java.time.LocalDateTime;

@Entity
@Table(name = "ACCOUNT_TYPE_TBL", schema = "db_sandbox")
@Data
@NoArgsConstructor
public class AccountType implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "sTypeName")
    private String name;

    @Column(name = "sUser")
    private String user;

    @Column(name = "dtTimestamp")
    private LocalDateTime timestamp;

    public AccountType(String name) {
        this.name = name;
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
