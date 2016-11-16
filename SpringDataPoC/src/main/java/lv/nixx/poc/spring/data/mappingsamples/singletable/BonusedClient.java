package lv.nixx.poc.spring.data.mappingsamples.singletable;

import javax.persistence.*;

@Entity(name="BonusedClient.ST")
@Table(schema="single_table_sample", name = "BonusedClient")
@DiscriminatorValue("BonusedClientType")
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
