package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lv.nixx.samples.json.ObjectMapperService;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ParseToMapTest {

    ObjectMapperService om = new ObjectMapperService();

    @Test
    void parseToMapSample() throws JsonProcessingException {

        String json = "{" +
                "  \"ACTION1\": {" +
                "    \"status\": \"OK\"," +
                "    \"description\": null" +
                "  }," +
                "  \"ACTION2\": {" +
                "    \"status\": \"FAIL\"," +
                "    \"description\": \"error 1\"" +
                "  }," +
                "  \"ACTION3\": {" +
                "    \"status\": \"FAIL\"," +
                "    \"description\": \"error 2\"" +
                "  }" +
                "}";

        Map<String, State> m = om.readValue(json, new TypeReference<>() {
        });

        assertThat(m)
                .usingRecursiveComparison()
                .isEqualTo(Map.of(
                        "ACTION1", new State("OK", null),
                        "ACTION2", new State("FAIL", "error 1"),
                        "ACTION3", new State("FAIL", "error 2")
                ));

    }

    @Getter
    static class State {
        final String status;
        final String description;

        @JsonCreator
        State(@JsonProperty("status") String status, @JsonProperty("description") String description) {
            this.status = status;
            this.description = description;
        }
    }


}
