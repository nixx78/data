package lv.nixx.samples.xml.jaxb;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class Configuration {

	@XmlElement(name = "name")
	private String name;

	@XmlJavaTypeAdapter(InstanceAdapter.class)
	@XmlElement(name = "instances")
	private Map<String, Instance> instances;

}
