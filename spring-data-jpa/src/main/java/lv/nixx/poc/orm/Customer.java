package lv.nixx.poc.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lv.nixx.poc.repository.auditable.Auditable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "CUSTOMER_TBL")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Customer implements Auditable {

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

    @Column(name = "dtTimestamp")
    private LocalDateTime timestamp;

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
                ", accounts=" + (accounts == null ? null : accounts.stream().map(Account::getName).collect(Collectors.joining())) +
                ", type=" + type +
                ", user=" + getUser() +
                '}';
    }

    @Override
    public void setUpdatedBy(String user) {
        this.user = user;
    }

    @Override
    public String getUpdatedBy() {
        return user;
    }

    @Override
    public void setUpdatedAt(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return timestamp;
    }
}
