package lv.nixx.poc.spring.data.domain;

import javax.persistence.*;

@Entity
@Table(name = "Adress")
public class Adress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	private Customer customer;

	private String line1;
	private String line2;

	public Adress() {
	}
	
	public Adress(String line1, String line2) {
		this.line1 = line1;
		this.line2 = line2;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
