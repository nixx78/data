package lv.nixx.poc.db.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "ACCOUNT", schema = "app")
@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "accountId")
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long accountId;

    @Column(name = "ACC_NUMBER")
    private String number;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ACCOUNT_ID")
    private Collection<Transaction> transactions;

    public Account(String number) {
        this.number = number;
    }

}
