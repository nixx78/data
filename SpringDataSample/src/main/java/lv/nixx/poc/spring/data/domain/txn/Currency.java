package lv.nixx.poc.spring.data.domain.txn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="CURRENCY")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = "alphaCode")
public class Currency {
	
	@Id
	@Column(name="alpha_code")
	private String alphaCode;
	
	@Column(name="numeric_code")
	private int numeric;

}
