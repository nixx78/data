package lv.nixx.samples.jaxb;

import java.util.*;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class InstancesAdapter extends XmlAdapter<Instances, Map<String, Instance>> {

	@Override
	public Instances marshal(Map<String, Instance> v) {
		System.out.println("marshal, map size: " + v.size());
		return null;
	}

	@Override
	public Map<String, Instance> unmarshal(Instances instance) {
		System.out.println("Instances: unmarshal, value: " + instance);
		return new HashMap<>();
	}

}
