package lv.nixx.poc.db.mappingsamples.joinedtable;

import javax.persistence.*;

@Entity(name = "BonusedClient.JS")
@Table(schema = "joined_table_sample", name = "BonusedClient")
@DiscriminatorValue("BonusedClientType.JS")
public class BonusedClient extends VipClient {

	private Long bonusSize;
	
	public BonusedClient() {
		super();
	}

	public BonusedClient(String name, String surname, Long vipClientLevel, Long bonusSize) {
		super(name, surname, vipClientLevel);
		this.bonusSize = bonusSize;
	}
	
	public Long getBonusSize() {
		return bonusSize;
	}

}
