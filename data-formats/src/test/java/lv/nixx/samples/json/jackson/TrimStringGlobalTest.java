package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TrimStringGlobalTest {

    private final ObjectMapper objectMapper;

    public TrimStringGlobalTest() {

        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new JsonDeserializer<>() {
            @Override
            public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                String value = jsonParser.getValueAsString();
                return (value != null) ? value.trim() : null;
            }
        });

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);

        this.objectMapper = objectMapper;
    }

    @Test
    void trimStringTest() throws JsonProcessingException {

        String json = objectMapper.writeValueAsString(new DataObject()
                .setAmount(BigDecimal.valueOf(10))
                .setField1("   Field1 ")
                .setField2("Field2")
        );

        DataObject dataObject = objectMapper.readValue(json, DataObject.class);

        assertThat(dataObject)
                .usingComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .usingRecursiveComparison()
                .isEqualTo(new DataObject()
                        .setAmount(BigDecimal.valueOf(10))
                        .setField1("Field1")
                        .setField2("Field2"));
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    static class DataObject {
        String field1;
        String field2;
        BigDecimal amount;
    }


}

