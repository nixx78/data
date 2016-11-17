package lv.nixx.poc.spring.data.domain;

import javax.persistence.Embeddable;
import javax.persistence.Table;

@Embeddable
@Table(name = "PersonAdditionalField")
public class PersonAdditionalField {

	private String field1;
	private String field2;

	public PersonAdditionalField() {
	}

	public PersonAdditionalField(String field1, String field2) {
		this.field1 = field1;
		this.field2 = field2;
	}

	public String getField1() {
		return field1;
	}

	public String getField2() {
		return field2;
	}

	@Override
	public String toString() {
		return "AdditionalFields [field1=" + field1 + ", field2=" + field2
				+ "]";
	}

}
