package lv.nixx.samples.jaxb;

import java.util.*;

import javax.xml.bind.annotation.*;

public class Instances {
	
	private List<Instance> list = new ArrayList<>();
	
	@XmlElement(name="instance")
	public List<Instance> getInstanceList() {
		return list;
	}

}
