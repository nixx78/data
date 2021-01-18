package lv.nixx.samples.jaxb;

import java.util.*;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class InstanceAdapter extends XmlAdapter<Instances, Map<String, Instance>> {
	
	@Override
	public Map<String, Instance> unmarshal(Instances instances) {
		Map<String, Instance> map = new TreeMap<>();
		for (Instance i: instances.getInstanceList()) {
			map.put(i.getId(), i);
		}
		return map;
	}

	@Override
	public Instances marshal(Map<String, Instance> v) {
		// TODO Auto-generated method stub
		return null;
	}


}
