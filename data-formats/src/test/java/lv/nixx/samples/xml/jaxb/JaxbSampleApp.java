package lv.nixx.samples.xml.jaxb;

import org.junit.Test;

import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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
		/* @formatter:off */
		String xml = 
				"<root>"
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
		/* @formatter:on */
		
		return xml;
	}
}
