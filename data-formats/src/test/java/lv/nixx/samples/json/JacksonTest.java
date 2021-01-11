package lv.nixx.samples.json;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.Test;

import lv.nixx.samples.json.domain.*;

public class JacksonTest {
	
	private ObjectMapperService objectMapper = new ObjectMapperService();

	@Test
	public void objectToJsonWithoutNullFieldsTest() throws Exception {

		Transaction p = new Transaction(10, 1, BigDecimal.valueOf(10.00), null, null);

		ObjectMapperService service = new ObjectMapperService();
		service.setSerializationInclusion(NON_NULL);
		service.enable(SerializationFeature.INDENT_OUTPUT);

		String s = service.writeValueAsString(p);
		// Null field will not be in JSON
		System.out.println(s);

		Transaction transaction = service.readValue(s, Transaction.class);

		System.out.println(transaction);

		assertNotNull(transaction);
		assertNull(transaction.getCurrency());
		assertEquals("Type should be set by default", Type.CREDIT, transaction.getType());
	}
		
	@Test
	public void objectToStringSample() throws Exception {
		Transaction p = new Transaction(10, 1, BigDecimal.valueOf(10.00), null, Currency.EUR);
		p.setDescription("Txn Description value");

		String jsonString = objectMapper.writeValueAsString(p);
		assertNotNull(jsonString);
		System.out.println(jsonString);
	}
	
	@Test
	public void stringToObjectSample() throws Exception {
		String json = "{\r\n" + 
				"  \"id\" : 10,\r\n" + 
				"  \"amount\" : 10.0,\r\n" + 
				"  \"currency\" : \"EUR\",\r\n" +
				"\"Txn description\" : \"Txn Description value\""+
				"}";
		
		Transaction txn = objectMapper.readValue(json, Transaction.class);
		assertNotNull(txn);
		
		System.out.println(txn);
	}
	
	@Test
	public void requiredFieldTest() throws Exception {
		String json = "{\r\n" + 
				"  \"id\" : 10,\r\n" + 
				"  \"amount\" : 10.0,\r\n" +
				"  \"currency\": null" +
				"}";
		
		Transaction txn = objectMapper.readValue(json, Transaction.class);
		assertNotNull(txn);
		
		System.out.println(txn);
	}

	@Test
	public void jsonNodeCompareSuccessTest() throws IOException {
		String expectedJson = "{\"id\":10,\"amount\":10.0}";
		String   actualJson = "{\"amount\":10.0,\"id\":10}";

		JsonNode expectedNode = objectMapper.readTree(expectedJson);
		JsonNode actualNode = objectMapper.readTree(actualJson);

		assertEquals(expectedNode, actualNode);
	}

	@Test
	public void jsonNodeCompareFailTest() throws IOException {
		String expectedJson = "{\"id\":10,\"amount\": 10.0}";
		String actualJson   = "{\"id\":10,\"amount\": 10.1}";

		JsonNode expectedNode = objectMapper.readTree(expectedJson);
		JsonNode actualNode = objectMapper.readTree(actualJson);

		assertNotEquals(expectedNode, actualNode);
	}

	@Test
	public void createJsonObjectOnFly() throws IOException {

		ObjectNode personObject = objectMapper.createObjectNode();
		personObject.put("name", "Name.Value");
		personObject.put("surname", "Surname.Value");

		personObject.set("value", new TextNode("Text.Value"));

		JsonNode nameField = personObject.get("name");
		System.out.println(nameField.asText());

		assertTrue(personObject.has("surname"));

		ObjectNode rootNode = objectMapper.createObjectNode();
		rootNode.set("person", personObject);

		System.out.println(rootNode);

		JsonNode expectedNode = objectMapper.readTree("{\"person\":{\"name\":\"Name.Value\",\"surname\":\"Surname.Value\",\"value\":\"Text.Value\"}}");
		assertEquals(expectedNode, rootNode);

		JsonNode person = expectedNode.get("person");

		String name = person.get("name").asText();
		System.out.println(name);

		String notExistingFieldValue = Optional.ofNullable(person.get("notExistingField")).map(JsonNode::asText).orElse(null);
		assertNull(notExistingFieldValue);
		assertFalse(person.has("notExistingField"));
	}



}
