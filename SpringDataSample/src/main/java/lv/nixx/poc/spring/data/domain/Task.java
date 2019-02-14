package lv.nixx.poc.spring.data.domain;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Task {

	// Данные поле мы не сохраняем в таблице, его, мы сами заполняем 
	// из поля, в которым хранится ключ для Map. 
	@Transient
	private String taskId;
	
	private String description;
	private String project;

	public Task() {
	}

	public Task(String taskId, String project, String description) {
		this.taskId = taskId;
		this.project = project;
		this.description = description;
	}

	public String getTaskId() {
		return taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", description=" + description + ", project=" + project + "]";
	}
}
