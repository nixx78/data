package lv.nixx.poc.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "CUSTOMER_TBL")
@Data
@NoArgsConstructor
public class Customer {

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "CustomerId")
    private Collection<Account> accounts;

    public Customer(String name, String surname, LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

}
