package lv.nixx.poc.db.domain.txn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionId implements Serializable {

    private String key1;
    private Long key2;
    private boolean  key3;

}
