package lv.nixx.poc.db.domain.txn;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "TXN_TABLE")
@Data
public class TransactionWithEmbeddedId {

    @EmbeddedId
    private EmbeddedTransactionId transactionId;

    private String data;

}
