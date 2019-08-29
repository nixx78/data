package lv.nixx.poc.db.domain.txn;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "TXN_ID_CLASS_TABLE")
@Data
@IdClass(TransactionId.class)
public class TransactionWithIdClass {

    @Id
    private String key1;

    @Id
    private Long key2;

    @Id
    private boolean  key3;

    private String data;

}
