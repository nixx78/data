package lv.nixx.poc.spring.data.mappingsamples.joinedtable;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity(name = "SalaryProjectClient.JS")
@Table(schema = "joined_table_sample", name = "SalaryProjectClient")
@DiscriminatorValue("SalaryProjectClientType.JS")
public class SalaryProjectClient extends SimpleClient {

	private BigDecimal salarySize;
	
	public SalaryProjectClient(){
		super();
	}
	
	public SalaryProjectClient(String name, String surname, String simpleClientDescription, BigDecimal salarySize) {
		super(name, surname, simpleClientDescription);
		this.salarySize = salarySize;
	}

	public BigDecimal getSalarySize() {
		return salarySize;
	}

}
