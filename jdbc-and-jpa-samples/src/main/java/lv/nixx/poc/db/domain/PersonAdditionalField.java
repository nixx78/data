package lv.nixx.poc.db.domain;

import javax.persistence.Embeddable;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Table(name = "PersonAdditionalField")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PersonAdditionalField {

	private String field1;
	private String field2;


}
