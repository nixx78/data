package lv.nixx.poc.txs.orm;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity(name = "BALANCE_TABLE")
@Accessors(chain = true)
public class AccountBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String accountId;
    private Date timestamp;

    private BigDecimal balance;

    @Column(nullable = false)
    private String updateUser;

}
