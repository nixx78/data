package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.Getter;
import lv.nixx.samples.json.ObjectMapperService;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeserializationStrictTest {

    private final ObjectMapperService objectMapperService = new ObjectMapperService();

    @Test
    public void deserializeTest() throws JsonProcessingException {

        String s = objectMapperService.writeValueAsString(new Alpha("1f", "2f"));

        Alpha alpha = objectMapperService.readValue(s, Alpha.class);
        assertNotNull(alpha);

        MismatchedInputException ex = assertThrows(MismatchedInputException.class,
                () -> objectMapperService.readValue("{\"x\":\"value\"}", Alpha.class)
        );

        assertEquals("Missing required creator property 'firstField' (index 0)\n" +
                " at [Source: (String)\"{\"x\":\"value\"}\"; line: 1, column: 13] (through reference chain: lv.nixx.samples.json.jackson.DeserializationStrictTest$Alpha[\"firstField\"])", ex.getMessage());
    }

    @Getter
    static class Alpha {
        String firstField;
        String secondField;

        Alpha(@JsonProperty(value = "firstField", required = true) String firstField,
              @JsonProperty(value = "secondField", required = true) String secondField) {
            this.firstField = firstField;
            this.secondField = secondField;
        }
    }

}
