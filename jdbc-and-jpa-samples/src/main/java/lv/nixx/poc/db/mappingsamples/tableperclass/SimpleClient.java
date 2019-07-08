package lv.nixx.poc.db.mappingsamples.tableperclass;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SimpleClient extends GenericBankClient {

	protected String simpleClientDescription;

	public SimpleClient() {
		super();
	}

	public SimpleClient(String name, String surname, String simpleClientDescription) {
		super(name, surname);
		this.simpleClientDescription = simpleClientDescription;
	}

}
