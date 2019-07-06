package lv.nixx.poc.db.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ADDRESS")
@NoArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String line1;
	private String line2;

	public Address(String line1, String line2) {
		this.line1 = line1;
		this.line2 = line2;
	}

}
