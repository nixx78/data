package lv.nixx.poc.db.mappingsamples.singletable;

import javax.persistence.MappedSuperclass;

import lv.nixx.poc.db.mappingsamples.singletable.GenericBankClient;

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
