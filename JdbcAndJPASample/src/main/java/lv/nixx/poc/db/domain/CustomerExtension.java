package lv.nixx.poc.db.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="CustomerExtension")
@ToString
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
	
}
