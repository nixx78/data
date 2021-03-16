package lv.nixx.samples.xml.jaxb;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class Instances {
	
	private List<Instance> list = new ArrayList<>();
	
	@XmlElement(name="instance")
	public List<Instance> getInstanceList() {
		return list;
	}

}
