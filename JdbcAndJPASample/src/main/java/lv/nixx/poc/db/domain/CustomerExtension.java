package lv.nixx.poc.db.domain;

import javax.persistence.*;

@Entity
@Table(name="CustomerExtension")
public class CustomerExtension {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Customer.class)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	private String additionalData;
	
	public CustomerExtension(){
	}

	public CustomerExtension(String additionalData) {
		this.additionalData = additionalData;
	}
	
	public String getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Extension [id=" + id + ", additionalData=" + additionalData + "]";
	}
	
}
