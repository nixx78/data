package lv.nixx.samples.jaxb;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "instance")
public class Instance {

	@XmlAttribute(name = "id")
	private String id;

	@XmlElement(name = "data")
	private String data;

	@XmlElement(name = "url")
	private String url;

	public String getId() {
		return id;
	}

	public String getData() {
		return data;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return id + ":" + data + ":" + url;
	}

}
