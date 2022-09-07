package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassWithMapTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void test() throws JsonProcessingException {

        ClassWithExtension c = new ClassWithExtension("key")
                .addValue(Term.WEEK1, 10)
                .addValue(Term.WEEK2, 20);

        assertEquals("{\"key\":\"key\",\"WEEK2\":20,\"WEEK1\":10}",  objectMapper.writeValueAsString(c));
    }


    @RequiredArgsConstructor
    @Getter
    @JsonSerialize(using = ClassWithExtensionSerializer.class)
    static class ClassWithExtension {
        final String key;
        Map<Term, Integer> values = new HashMap<>();

        ClassWithExtension addValue(Term term, int value) {
            values.put(term, value);
            return this;
        }
    }

    enum Term {
        WEEK1, WEEK2,
    }

    static class ClassWithExtensionSerializer extends StdSerializer<ClassWithExtension> {

        public ClassWithExtensionSerializer() {
            this(null);
        }

        public ClassWithExtensionSerializer(Class<ClassWithExtension> t) {
            super(t);
        }

        @Override
        public void serialize(ClassWithExtension value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

            jgen.writeStartObject();
            jgen.writeStringField("key", value.key);

            for (Map.Entry<Term, Integer> e : value.getValues().entrySet()) {
                jgen.writeObjectField(e.getKey().name(), e.getValue());
            }
            jgen.writeEndObject();
        }
    }

}
