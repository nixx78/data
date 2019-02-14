package lv.nixx.poc.db.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
/* Поля данного класса будут являтся колонками таблицы, которая будет создана для класса с @Embeded */
public class PersonExtension  {
	
	@Column(name="line1")
	private String extensionLine1;
	
	@Column(name="line2")
	private String extensionLine2;
	
	public PersonExtension(){
	}

	public PersonExtension(String extensionLine1, String extensionLine2) {
		this.extensionLine1 = extensionLine1;
		this.extensionLine2 = extensionLine2;
	}

	public String getExtensionLine1() {
		return extensionLine1;
	}

	public String getExtensionLine2() {
		return extensionLine2;
	}

	@Override
	public String toString() {
		return "PersonExtension [extensionLine1=" + extensionLine1 + ", extensionLine2=" + extensionLine2 + "]";
	}

}
