package lv.nixx.samples.yaml;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class YamlSample {

    @Test
    public void loadAndParseYAML() throws IOException {

        ObjectMapper om = new ObjectMapper(new YAMLFactory());

        YamlObject yamlObj = om.readValue(new File("src/test/resources/sample.yaml"), YamlObject.class);

        assertAll(
                () -> assertEquals("StringValue", yamlObj.getValue()),
                () -> assertEquals(3, yamlObj.getMappedValues().size())
        );

    }

    @Data
    static class YamlObject {
        private String value;
        private Map<String, String> mappedValues;

        @JsonCreator
        public YamlObject(@JsonProperty("value") String value,
                          @JsonProperty("mappedValues") Map<String, String> mappedValues) {
            this.value = value;
            this.mappedValues = mappedValues;
        }
    }

}
