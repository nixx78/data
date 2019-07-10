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

	@Transient				// This column will be created automatically
	private String taskId;

	private String description;
	private String project;

}
