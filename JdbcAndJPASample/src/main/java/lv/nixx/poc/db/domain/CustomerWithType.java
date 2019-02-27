package lv.nixx.poc.db.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class CustomerWithType {

	private Long id;
	private String firstName;
	private String lastName;
	private String segment;
	private String typeId;
	private String description;

}
