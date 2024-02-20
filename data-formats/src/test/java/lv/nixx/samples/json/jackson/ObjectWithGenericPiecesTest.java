package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lv.nixx.samples.json.ObjectMapperService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

class ObjectWithGenericPiecesTest {

    private final ObjectMapperService om;

    public ObjectWithGenericPiecesTest() {
        this.om = new ObjectMapperService();
        this.om.configure(SerializationFeature.INDENT_OUTPUT, true);
        this.om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.om.writerWithDefaultPrettyPrinter();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(new LocalDateTimeSerializer(dateTimeFormatter));
        this.om.registerModule(module);
    }

    @Test
    void test() throws JsonProcessingException, JSONException {

        String p1 = "{\"k1\": \"v1\", \"kn1\": 10}";
        String p2 = "{\"k2\": \"v2\", \"kn2\": 20}";
        String p3 = "{\"k3\": \"v3\", \"kn3\": 30}";

        ObjectWithDifferentPayloads w = new ObjectWithDifferentPayloads("INIT", LocalDateTime.parse("2024-02-21T10:00:00"));

        w.setPayload(
                List.of(
                        om.readTree(p1),
                        om.readTree(p2),
                        om.readTree(p3)
                )
        );

        String jsonAsString = om.writeValueAsString(w);
        System.out.println("JSON as String: \n" + jsonAsString);

        JSONAssert.assertEquals("""
                { "action" : "INIT",
                  "timestamp" : "2024-02-21T10:00:00.000",
                  "payload" : [{"k1":"v1","kn1":10}, {"k2":"v2","kn2":20}, {"k3":"v3","kn3":30}]
                }""", jsonAsString, false);

        HashMap<String, Object> hashMap = om.readValue(jsonAsString, HashMap.class);

        System.out.println("JSON as HashMap: \n" + hashMap);

    }

    @Data
    @RequiredArgsConstructor
    static class ObjectWithDifferentPayloads {

        final String action;
        final LocalDateTime timestamp;

        @JsonRawValue
        Collection<JsonNode> payload;

    }

}
