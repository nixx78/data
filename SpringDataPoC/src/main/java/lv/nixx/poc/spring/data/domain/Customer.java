package lv.nixx.poc.spring.data.domain;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name="Customer")
@NamedQueries( {@NamedQuery(name="Customer.selectAllCustomersQuery", query="Select c from Customer c"),
				@NamedQuery(name="Customer.findCustomerByLastName", query="Select c from Customer c where lastName=:lastName")})
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

	public CustomerExtension getExtension() {
		return extension;
	}
	
	public Long getId() {
		return id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setExtension(CustomerExtension extension) {
		this.extension = extension;
		if (extension != null) {
			this.extension.setCustomer(this);
		}
	}
	
	public void addAdress(Adress adress){
		adress.setCustomer(this);
		this.adress.add(adress);
	}
	
	public Set<Adress> getAdress() {
		return adress;
	}

	public CustomerType getType() {
		return this.type;
	}
	
	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", segment=" + segment + ", type="
				+ type + ", extension=" + extension + ", adress=" + adress
				+ "]";
	}

	
	
    

}
