package lv.nixx.poc.db.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;


/**
 * Класс для хранения сущности Person, демонстрируются следующие связи (работает начиная с JPA 2.0): <br>
 *  <li> @Embedded - встраивание полей другого класса в таблицу <br>
 *  <li> @ElementCollection - хранения дополнительных объектов в Set и Map <br>
 * <br>
 * Также, показано, что можно выносить поля в супер класс (с аннотацией @MappedSuperclas).
 * Примечание: класс AbstractPersistable - является частью Spring Data JPA, 
 * его нельзя  использовать, если нужен "чистый" JPA  
 * 
 * @author Nikolajs
 *
 */

@Entity
@Table(name="PERSON")
@Cacheable(false)
@ToString
@Data
public class Person {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Version
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int version;
	
	private String name;
	private String surname;

	/**
	 * Пример использование возможности встраивания полей в таблицу
	 */
	@Embedded
	private PersonExtension extension;
	
	
	/**
	 * Пример cвязи 1->n, в качестве коллекции используется Set содержащий строки, данные хранятся 
	 * в отдельной таблице, имя которой указано в аннотации @CollectionTable 
	 */
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "ALIAS_TABLE")
	private Set<String> alias = new TreeSet<>();

	/* Данные из коллекции хранятся в отдельной таблице, имя которой мы указали в аннотации, обязательное условие, для класса AdditionalField
	 * должна стоять аннотация @Embeddable
	 */
	
	/**
	 * Можно также, пример cвязи 1->n, в качестве коллекции используется Set содержащий строки, данные хранятся 
	 * в отдельной таблице, имя которой указано в аннотации @CollectionTable 
	 */
	@ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "PERSON_ADDITIONAL_FIELD")
	private Set<PersonAdditionalField> additionalFields = new HashSet<>();

	/** 
	 * Связь 1->n, данные хранятся в Map, при этом, значения хранятся в классе Task (в классе должна стоять аннтоция @Embeddable) 
	 */
	@ElementCollection(fetch=FetchType.EAGER)
	// Название колонки, в которой будет хранится ключ Map
	@MapKeyColumn(name="task_id") 
    @CollectionTable(name="TASK", joinColumns=@JoinColumn(name="person_id"))
	// Название таблицы и колонки, в которой будет хранится ключ, по которому связываем с таблицей Person
	private Map<String, Task> tasks = new HashMap<>();

	public Person(){
	}

	public Person(String name, String surname, PersonExtension ext) {
		this.name = name;
		this.surname = surname;
		this.extension = ext;
	}

	public void addAlias(String alias){
		this.alias.add(alias);
	}

	public void addAdditionalField(PersonAdditionalField additionalField) {
		this.additionalFields.add(additionalField);
	}
	
	public void addTask(Task task){
		this.tasks.put(task.getTaskId(), task);
	}
	

}
