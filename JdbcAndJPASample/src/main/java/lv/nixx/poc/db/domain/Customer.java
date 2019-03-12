package lv.nixx.poc.db.domain;

import java.util.*;

import javax.persistence.*;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="Customer")
@NamedQueries( 
		{
			@NamedQuery(name="Customer.selectAllCustomersQuery", query="Select c from Customer c"),
			@NamedQuery(name="Customer.findCustomerByLastName", query="Select c from Customer c where lastName=:lastName")
		}
)

@NamedNativeQueries(value = { 
		@NamedNativeQuery(
		resultSetMapping = "resultSetMapping", 
		name = "customersWithType", 
		query = " SELECT c.ID, FIRSTNAME, LASTNAME, SEGMENT, TYPE_ID, DESCRIPTION "
				+ "FROM app.CUSTOMER C, app.CUSTOMERTYPE CT "
				+ "WHERE C.TYPE_ID =  CT.ID"
				),
		
		@NamedNativeQuery(
				resultSetMapping = "resultSetMapping", 
				name = "customerByFirstName",
				query = " SELECT c.ID, FIRSTNAME, LASTNAME, SEGMENT, TYPE_ID, DESCRIPTION "
						+ "FROM app.CUSTOMER C, app.CUSTOMERTYPE CT "
						+ "WHERE C.TYPE_ID =  CT.ID AND FIRSTNAME=:firstname"
		)
		
})
@SqlResultSetMapping(name = "resultSetMapping",
classes = {
		 @ConstructorResult(
           targetClass=CustomerWithType.class,
             columns={
                @ColumnResult(name="ID", type = Long.class),
                @ColumnResult(name="FIRSTNAME"),
                @ColumnResult(name="LASTNAME"),
                @ColumnResult(name="SEGMENT"),
                @ColumnResult(name="TYPE_ID"),
                @ColumnResult(name="DESCRIPTION")
            }
         )
}
)

@Data
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(name="firstName", nullable=false)
    private String firstName;
    
    @Column(name="lastName", nullable=false)
    private String lastName;

    @ManyToOne
    private CustomerType type;
    
    
    @OneToOne(fetch = FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="customer", targetEntity=CustomerExtension.class, orphanRemoval=true)
    private CustomerExtension extension;
    
    // Очень важно, устанавливать параметер orphanRemoval=true, без него, удаления происходит только в одной таблице CUSTOMER_ADDRESS, 
    // в таблице ADRESS записи остаются.
    @OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
    private Set<Adress> adress = new HashSet<>();
    
    @Enumerated(EnumType.STRING)
    private Segment segment = Segment.REGULAR;
    
    protected Customer() {
    }

    public Customer(String firstName, String lastName, CustomerType customerType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = customerType;
    }

    public Customer(String firstName, String lastName, CustomerType customerType, CustomerExtension extension) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = customerType;
        setExtension(extension);
    }

    public void addAdress(Adress adress){
		this.adress.add(adress);
	}

}
