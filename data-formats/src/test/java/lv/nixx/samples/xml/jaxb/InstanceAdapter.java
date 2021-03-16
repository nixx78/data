package lv.nixx.samples.xml.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Map;
import java.util.TreeMap;

public class InstanceAdapter extends XmlAdapter<Instances, Map<String, Instance>> {
	
	@Override
	public Map<String, Instance> unmarshal(Instances instances) throws Exception {
		Map<String, Instance> map = new TreeMap<>();
		for (Instance i: instances.getInstanceList()) {
			map.put(i.getId(), i);
		}
		return map;
	}

	@Override
	public Instances marshal(Map<String, Instance> v) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
