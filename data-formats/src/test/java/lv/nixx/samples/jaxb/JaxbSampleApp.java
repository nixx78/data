package lv.nixx.samples.jaxb;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.Map;

import javax.xml.bind.JAXB;

import org.junit.Test;

public class JaxbSampleApp {

	@Test
	public void testShouldCreateObjectFromXML() {

		StringReader reader = new StringReader(getXMLSample());

		Configuration config = JAXB.unmarshal(reader, Configuration.class);

		System.out.println("root config name: " + config.getName());
		Map<String, Instance> instances = config.getInstances();

		int size = instances.size();
		assertEquals("Instance count", 2, size);

		for (Instance instanceConfig : instances.values()) {
			System.out.println(instanceConfig);
		}

	}

	private static String getXMLSample(){
		return  "<root>"
				+ "<name>Name123</name>"
					+ "<instances>"
						+ "<instance id='100'>"
							+ "<data>d100</data>"
							+ "<url>localhost100</url>"
						+ "</instance>"
						+ "<instance id='200'>"
							+ "<data>d200</data>"
							+ "<url>localhost200</url>"
						+ "</instance>"
					 + "</instances>"
				+ "</root>";
	}
}
