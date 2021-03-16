package lv.nixx.samples.xml.sax;

import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/* 
 * Основной принцип работы SAX - получение событий при обходе XML, 
 * и реакция на эти события. Чтения всего XML в память не происходит, только по мере обработки.
 */

public class SAXParsingSample extends DefaultHandler {

	public Map<String, BigDecimal> totalByCurrency = new AmountsMap();

	private String currentCurrency;
	private BigDecimal currentAmount;

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		if (localName.equalsIgnoreCase("amount")) {
			currentCurrency = atts.getValue("currency");
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equalsIgnoreCase("amount")) {
			totalByCurrency.put(currentCurrency, this.currentAmount);
		}
		currentAmount = BigDecimal.ZERO;
		currentCurrency = null;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (currentCurrency != null) {
			final String amountAsString = new String(Arrays.copyOfRange(ch, start, start + length));
			currentAmount = new BigDecimal(amountAsString);
		}
	}

	@Test
	public void parseAndCalculateAmounts() throws Exception {
		SAXParsingSample ps = new SAXParsingSample();

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);

		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(ps);

		InputSource inputSource = new InputSource(new StringReader(getXMLSampleString()));
		xmlReader.parse(inputSource);

		System.out.println(ps.totalByCurrency.toString());

		assertEquals(39.0d,  ps.totalByCurrency.get("USD").doubleValue(), 0);
		assertEquals(37.06d, ps.totalByCurrency.get("EUR").doubleValue(), 0);
	}

	private static String getXMLSampleString() {
		/* @formatter:off */
		String xml = "<root>" + 
						"<amount currency='USD'>10.27</amount>" + 
						"<amount currency='EUR'>30.56</amount>" + 
						"<amount currency='USD'>1.73</amount>" + 
						"<amount currency='EUR'>3.20</amount>" + 
						"<amount currency='USD'>27.00</amount>" + 
						"<amount currency='EUR'>3.30</amount>" + 
					 "</root>";
		/* @formatter:on */

		return xml;
	}

	class AmountsMap extends HashMap<String, BigDecimal> {

		private static final long serialVersionUID = 1L;

		@Override
		public BigDecimal get(Object key) {
			final BigDecimal existingKey = super.get(key);
			return existingKey == null ? BigDecimal.ZERO : existingKey;
		}

		@Override
		public BigDecimal put(String currency, BigDecimal amount) {
			BigDecimal existingAmount = get(currency);
			return super.put(currency, existingAmount.add(amount));
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();

			Set<Entry<String, BigDecimal>> entrySet = entrySet();
			for (Entry<String, BigDecimal> entry : entrySet) {
				sb.append("Сurrency \"" + entry.getKey() + "\" amount " + entry.getValue() + "\n");
			}

			return sb.toString();
		}

	}

}
