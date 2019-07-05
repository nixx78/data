package lv.nixx.poc.db.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "ACCOUNT", schema = "app")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "accountId")
@ToString(exclude = "transactions")
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long accountId;

    @Column(name = "ACC_NUMBER")
    private String number;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Transaction> transactions = new ArrayList<>();

    public Account(String number) {
        this.number = number;
    }

    public void addTransaction(Collection<Transaction> txns) {
        txns.forEach(this::addTransaction);
    }

    public void addTransaction(Transaction txn) {
        this.transactions.add(txn);
        if (txn.getAccount() != this) {
            txn.setAccount(this);
        }
    }

    public void removeTransaction() {
        this.transactions.clear();
    }

}
