package lv.nixx.samples.xml.dom;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;


/* 
 * Создаем XML Document, применяем к нему XPath, получаем значения.
 * Весь XML, загружается   
 * 
 * */

public class DOMSample {

	@Test
	public void testShouldCreateDocumentAndApplyXPath() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = dbf.newDocumentBuilder();

		/* @formatter:off */
		StringReader sr = new StringReader(
				"<root>"
					+ "<value type='x'>ValueX</value>"
					+ "<value type='y'>ValueY</value>"
			  + "</root>");
		/* @formatter:on */

		InputSource is = new InputSource(sr);
		Document doc = documentBuilder.parse(is);

		Element rootElement = doc.getDocumentElement();
		assertEquals("root", rootElement.getNodeName());

		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath newXPath = xpathFactory.newXPath();

		// Get one Element value using XPath
		XPathExpression xpathExpression = newXPath.compile("value[@type='y']");
		assertEquals("ValueY", xpathExpression.evaluate(rootElement));

		// Get values list from Document
		XPathExpression valuesListExpression = newXPath.compile("//value");

		NodeList values = (NodeList) valuesListExpression.evaluate(doc, XPathConstants.NODESET);
		assertEquals(2, values.getLength());
		
		for (int i = 0; i < values.getLength(); i++) {
			if (values.item(i).getNodeType()==Element.ELEMENT_NODE){
				Element item = (Element) values.item(i);
				System.out.println(item.getTextContent());				
			}
		}

	}
}
