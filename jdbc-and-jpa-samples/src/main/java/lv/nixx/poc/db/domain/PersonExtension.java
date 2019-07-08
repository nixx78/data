package lv.nixx.poc.db.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
/* Поля данного класса будут являтся колонками таблицы, которая будет создана для класса с @Embeded */

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PersonExtension  {
	
	@Column(name="line1")
	private String extensionLine1;
	
	@Column(name="line2")
	private String extensionLine2;

}
