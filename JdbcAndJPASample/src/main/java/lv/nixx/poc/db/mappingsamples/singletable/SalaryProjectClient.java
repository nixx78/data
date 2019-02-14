package lv.nixx.poc.db.mappingsamples.singletable;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity(name="SalaryProjectClient.ST")
@Table(schema="single_table_sample", name = "BonusedClient")
@DiscriminatorValue("SalaryProjectClient")
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
