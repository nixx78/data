package lv.nixx.poc.db.mappingsamples.tableperclass;

import javax.persistence.*;

@Entity(name = "BonusedClient.TPC")
@Table(schema = "table_per_class_sample", name = "BonusedClient")
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
