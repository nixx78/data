package lv.nixx.poc.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lv.nixx.poc.repository.useraware.UserAware;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "CUSTOMER_TBL")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Customer implements UserAware {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "sName")
    private String name;

    @Column(name = "sSurname")
    private String surname;

    @Column(name = "dtDateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "sType")
    private String type;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Account> accounts = new HashSet<>();

    @Column(name = "sUser")
    private String user;

    public Customer(String name, String surname, LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", accounts=" + accounts == null ? null : accounts.stream().map(Account::getName).collect(Collectors.joining()) +
                ", type=" + type +
                '}';
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }
}
