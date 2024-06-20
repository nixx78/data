package lv.nixx.poc.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "ACCOUNT_TBL")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "sName")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId", referencedColumnName = "id", updatable = false, nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typeId", referencedColumnName = "id")
    private AccountType type;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountId", referencedColumnName = "id", updatable = false)
    private Set<Transaction> transactions;

    public Account(String name, Customer customer, AccountType type) {
        this.name = name;
        this.customer = customer;
        this.type = type;
    }
}
