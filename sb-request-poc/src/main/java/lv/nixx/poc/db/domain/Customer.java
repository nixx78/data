package lv.nixx.poc.db.domain;

import lombok.Data;
import lombok.ToString;
import lv.nixx.poc.db.mapping.CustomerWithType;

import javax.persistence.*;


@SqlResultSetMapping(name = "customerWithTypeMapping",
        classes = {
                @ConstructorResult(
                        targetClass = CustomerWithType.class,
                        columns = {
                                @ColumnResult(name = "ID", type = Long.class),
                                @ColumnResult(name = "FIRSTNAME"),
                                @ColumnResult(name = "LASTNAME"),
                                @ColumnResult(name = "TYPE_ID"),
                                @ColumnResult(name = "DESCRIPTION")
                        }
                )
        }
)


@Entity
@Table(name = "Customer")
@Data
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @ManyToOne
    private CustomerType type;

    public Customer() {
    }

    public Customer(String firstName, String lastName, CustomerType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

}
