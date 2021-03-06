package lv.nixx.poc.db.mappingsamples.joinedtable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class VipClient extends GenericBankClient {
	
	private Long vipClientLevel;

	public VipClient() {
		super();
	}

	public VipClient(String name, String surname, Long vipClientLevel) {
		super(name, surname);
		this.vipClientLevel = vipClientLevel;
	}

	public Long getVipClientLevel() {
		return vipClientLevel;
	}

}
