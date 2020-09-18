package lv.nixx.poc.txs.data.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "BALANCE_TABLE")
public class AccountBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String accountId;
    private Date timestamp;

    @Column(nullable = false)
    private String updateUser;

}
