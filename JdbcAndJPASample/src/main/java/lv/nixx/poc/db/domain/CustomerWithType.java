package lv.nixx.poc.db.domain;

public class CustomerWithType {

	private Long id;
	private String firstName;
	private String lastName;
	private String segment;
	private String typeId;
	private String description;

	public CustomerWithType(Long id, String firstName, String lastName, String segment, String typeId,
			String description) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.segment = segment;
		this.typeId = typeId;
		this.description = description;
	}

	@Override
	public String toString() {
		return "CustomerWithType [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", segment="
				+ segment + ", typeId=" + typeId + ", description=" + description + "]";
	}

}
