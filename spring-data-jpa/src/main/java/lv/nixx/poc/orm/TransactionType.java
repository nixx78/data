package lv.nixx.poc.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.nixx.poc.repository.useraware.UserAware;

@Entity
@Table(name = "TRANSACTION_TYPE_TBL")
@Data
@NoArgsConstructor
public class TransactionType implements UserAware {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "sTypeName")
    private String typeName;

    @Column(name = "sUser")
    private String user;

    public TransactionType(String typeName) {
        this.typeName = typeName;
    }

}
