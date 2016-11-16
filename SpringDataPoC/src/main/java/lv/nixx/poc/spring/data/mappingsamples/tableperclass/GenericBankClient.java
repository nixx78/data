package lv.nixx.poc.spring.data.mappingsamples.tableperclass;

import javax.persistence.*;

@Entity(name="BankClientTablePerclassSample")
@Table(schema = "TablePerClassSample",  name = "GenericClient")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GenericBankClient {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long clientId;

	private String name;
	private String surname;

	@Column(insertable = false, updatable = false)
	public String clientType;

	protected GenericBankClient() {
	}

	protected GenericBankClient(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}

	public Long getClientId() {
		return clientId;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

}
