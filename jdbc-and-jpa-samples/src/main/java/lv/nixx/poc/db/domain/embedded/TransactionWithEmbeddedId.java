package lv.nixx.poc.db.domain.embedded;

import lombok.Builder;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TXN_TABLE")
@Data
@Builder
public class TransactionWithEmbeddedId {

    @EmbeddedId
    private EmbeddedTransactionId transactionId;

    private String data;

}
