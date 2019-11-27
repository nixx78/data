package lv.nixx.poc.db.domain.txn;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TXN_ID_CLASS_TABLE")
@Data
@Accessors(chain = true)
@IdClass(TransactionId.class)
public class TransactionWithIdClass {

    @Id
    private String key1;

    @Id
    private Long key2;

    @Id
    private boolean  key3;

    private String data;

    private BigDecimal amount;

    private String type;

}
