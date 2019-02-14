package lv.nixx.poc.spring.data.mappingsamples.joinedtable;

import javax.persistence.*;

@Entity(name = "Student.JS")
@Table(schema = "joined_table_sample", name="Student")
@DiscriminatorValue("StudentType.JS")
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
