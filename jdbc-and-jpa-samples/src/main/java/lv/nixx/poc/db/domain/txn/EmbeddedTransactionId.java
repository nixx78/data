package lv.nixx.poc.db.domain.txn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmbeddedTransactionId implements Serializable {

    private String key1;
    private Long key2;
    private boolean  key3;

}
