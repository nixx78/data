package lv.nixx.samples.jaxb;

import java.util.Map;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Configuration {

	@XmlElement(name = "name")
	private String name;

	@XmlJavaTypeAdapter(InstanceAdapter.class)
	@XmlElement(name = "instances")
	private Map<String, Instance> instances;

	public Map<String, Instance> getInstances() {
		return instances;
	}

	public String getName() {
		return name;
	}

}
