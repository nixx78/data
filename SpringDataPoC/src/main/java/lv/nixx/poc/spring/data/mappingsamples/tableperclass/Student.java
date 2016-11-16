package lv.nixx.poc.spring.data.mappingsamples.tableperclass;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Student.TPC")
@Table(schema = "table_per_class_sample", name="Student")
public class Student extends SimpleClient {

	public Long studentNumber;

	public Student(){
		super();
	}
	
	public Student(String name, String surname, String simpleClientDescription, Long studentNumber) {
		super(name, surname, simpleClientDescription);
		this.studentNumber = studentNumber;
	}

	public Long getStudentNumber() {
		return studentNumber;
	}

	@Override
	public String toString() {
		return "Student [studentNumber=" + studentNumber
				+ ", simpleClientDescription=" + simpleClientDescription
				+ ", clientType=" + clientType + ", getClientId()="
				+ getClientId() + ", getName()=" + getName()
				+ ", getSurname()=" + getSurname() + "]";
	}

	
}
