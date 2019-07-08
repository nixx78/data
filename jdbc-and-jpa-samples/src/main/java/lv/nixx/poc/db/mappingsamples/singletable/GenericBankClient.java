package lv.nixx.poc.db.mappingsamples.singletable;

import javax.persistence.*;

@Entity
@Table(schema = "single_table_sample",  name = "BankClient")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "clientType")
public abstract class GenericBankClient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
