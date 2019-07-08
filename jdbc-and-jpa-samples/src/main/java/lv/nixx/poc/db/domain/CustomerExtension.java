package lv.nixx.poc.db.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="CUSTOMER_EXTENSION")
@Data
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

	public String toString() {
		return "CustomerExtension(id=" + this.getId() + ", additionalData=" + this.getAdditionalData() + ")";
	}
}
