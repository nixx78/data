package lv.nixx.samples.json.jackson;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lv.nixx.samples.json.ObjectMapperService;

import lv.nixx.samples.json.domain.*;
import org.junit.jupiter.api.Test;

class JacksonTest {

    private final ObjectMapperService objectMapper = new ObjectMapperService();

    @Test
    void objectToJsonWithoutNullFieldsTest() throws Exception {

        Transaction p = new Transaction(10, 1, BigDecimal.valueOf(10.00), null, null, LocalDateTime.parse("2023-04-05T12:00:00"));

        ObjectMapperService service = new ObjectMapperService();
        service.setSerializationInclusion(NON_NULL);
        service.enable(SerializationFeature.INDENT_OUTPUT);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(new LocalDateTimeSerializer(dateTimeFormatter));
        service.registerModule(module);

        String s = service.writeValueAsString(p);
        // Null field will not be in JSON
        System.out.println("Transaction as JSON:\n" + s);

        Transaction transaction = service.readValue(s, Transaction.class);

        System.out.println(transaction);

        assertNotNull(transaction);
        assertNull(transaction.getCurrency());
        assertEquals(Type.CREDIT, transaction.getType(), "Type should be set by default");
    }

    @Test
    void objectToStringSample() throws Exception {
        Transaction p = new Transaction(10, 1, BigDecimal.valueOf(10.00), null, Currency.EUR);
        p.setDescription("Txn Description value");

        String jsonString = objectMapper.writeValueAsString(p);
        assertNotNull(jsonString);
        System.out.println(jsonString);
    }

    @Test
    void objectToStringWithDateFormatTest() throws Exception {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        ObjectMapperService om = new ObjectMapperService();
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        om.findAndRegisterModules();

        String json = om.writeValueAsString(new Person()
                .setId(100)
                .setName("name")
                .setDateOfBirth(df.parse("01.12.1978 13:00:01"))
                .setSalary(BigDecimal.valueOf(100))
                .setTimestamp(LocalDateTime.parse("2023-02-14T12:01:02"))
        );
        System.out.println(json);

    }

    @Test
    void stringToObjectSample() throws Exception {
        String json = "{\r\n" +
                "  \"id\" : 10,\r\n" +
                "  \"amount\" : 10.0,\r\n" +
                "  \"currency\" : \"EUR\",\r\n" +
                "\"Txn description\" : \"Txn Description value\"" +
                "}";

        Transaction txn = objectMapper.readValue(json, Transaction.class);
        assertNotNull(txn);

        System.out.println(txn);
    }

    @Test
    void requiredFieldTest() throws Exception {
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
    void jsonNodeCompareSuccessTest() throws IOException {
        String expectedJson = "{\"id\":10,\"amount\":10.0}";
        String actualJson = "{\"amount\":10.0,\"id\":10}";

        JsonNode expectedNode = objectMapper.readTree(expectedJson);
        JsonNode actualNode = objectMapper.readTree(actualJson);

        assertEquals(expectedNode, actualNode);
    }

    @Test
    void jsonNodeCompareFailTest() throws IOException {
        String expectedJson = "{\"id\":10,\"amount\": 10.0}";
        String actualJson = "{\"id\":10,\"amount\": 10.1}";

        JsonNode expectedNode = objectMapper.readTree(expectedJson);
        JsonNode actualNode = objectMapper.readTree(actualJson);

        assertNotEquals(expectedNode, actualNode);
    }

    @Test
    void createJsonObjectOnFly() throws IOException {

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
