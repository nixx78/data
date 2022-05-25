package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.experimental.Accessors;
import lv.nixx.samples.json.ObjectMapperService;
import lv.nixx.samples.json.domain.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeAsStringTest {

    private final ObjectMapperService om = new ObjectMapperService();

    @Test
    void getNodeAsStringTest() throws JsonProcessingException {


        String json = "{" +
                "  \"field1\" : \"Field1.Value\",\n" +
                "  \"payload\" : {\n" +
                "    \"id\" : 1,\n" +
                "    \"name\" : \"Name1\",\n" +
                "    \"dateOfBirth\" : null,\n" +
                "    \"salary\" : null\n" +
                "  }\n" +
                "}";

        Wrapper wrapper = om.readValue(json, Wrapper.class);

        assertAll(
                () -> assertEquals("Field1.Value", wrapper.field1),
                () -> assertEquals("{\"id\":1,\"name\":\"Name1\",\"dateOfBirth\":null,\"salary\":null}", wrapper.payload)
        );

    }

    @Test
    void test() throws JsonProcessingException {
        Wrapper w = new Wrapper()
                .setField1("Field1.Value")
                .setPayload(om.writeValueAsString(
                        new Person()
                                .setId(1)
                                .setName("Name1"))
                );

        String s = om.writeValueAsString(w);

        Wrapper dsr = om.readValue(s, Wrapper.class);

        assertAll(
                () -> assertEquals("Field1.Value", dsr.field1),
                () -> assertEquals("{\"id\":1,\"name\":\"Name1\",\"dateOfBirth\":null,\"salary\":null}", dsr.payload)
        );

    }

    @Data
    @Accessors(chain = true)
    static class Wrapper {
        String field1;

        // This property is only for serialization
        @JsonRawValue
        String payload;

        @JsonProperty("payload")
        private void unpackPayload(JsonNode payload) {
            this.payload = payload.toString();
        }
    }

}
