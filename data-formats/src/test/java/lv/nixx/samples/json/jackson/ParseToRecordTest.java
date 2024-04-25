package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParseToRecordTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void recordSandbox() throws JsonProcessingException {

        String personAsString = objectMapper.writeValueAsString(new Person("name.value", "surname.value"));

        assertEquals("{\"nameProp\":\"name.value\",\"surnameProp\":\"surname.value\"}", personAsString);

        Person person = objectMapper.readValue(personAsString, Person.class);
        assertAll(
                () -> assertEquals("name.value", person.name()),
                () -> assertEquals("surname.value", person.surname())
        );

        assertEquals("{\"nameProp\":\"n1\",\"surnameProp\":\"s1\"}",
                objectMapper.writeValueAsString(Person.builder()
                        .name("n1")
                        .surname("s1")
                        .build())
        );

    }

    @Builder
    record Person(@JsonProperty("nameProp") String name, @JsonProperty("surnameProp") String surname) {
    }

}
