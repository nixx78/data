package lv.nixx.samples.xml.jaxb;

import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "instance")
@Getter
@ToString
public class Instance {

	@XmlAttribute(name = "id")
	private String id;

	@XmlElement(name = "data")
	private String data;

	@XmlElement(name = "url")
	private String url;
}
