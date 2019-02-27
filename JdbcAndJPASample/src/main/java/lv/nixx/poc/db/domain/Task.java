package lv.nixx.poc.db.domain;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Task {

	// Данные поле мы не сохраняем в таблице, его, мы сами заполняем 
	// из поля, в которым хранится ключ для Map. 
	@Transient
	private String taskId;
	
	private String description;
	private String project;

}
