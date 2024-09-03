package lv.nixx.poc.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.nixx.poc.repository.useraware.UserAware;

@Entity
@Table(name = "ACCOUNT_TYPE_TBL")
@Data
@NoArgsConstructor
public class AccountType implements UserAware {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "sTypeName")
    private String name;

    @Column(name = "sUser")
    private String user;

    public AccountType(String name) {
        this.name = name;
    }

}
