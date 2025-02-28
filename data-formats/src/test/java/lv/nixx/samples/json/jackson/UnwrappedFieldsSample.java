package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Collection;
import java.util.List;

class UnwrappedFieldsSample {

    private final ObjectMapper objectMapper;

    public UnwrappedFieldsSample() {
        this.objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    void unwrapSample() throws JsonProcessingException, JSONException {

        EmbeddedClassAlpha alpha = new EmbeddedClassAlpha("aValue1", "aValue2");

        BaseClass baseClass = new BaseClass(alpha, List.of(
                new EmbeddedClassBeta("bValue11", "bValue12"), new EmbeddedClassBeta("bValue21", "bValue22")
        ));

        String s = objectMapper.writeValueAsString(baseClass);

        JSONAssert.assertEquals(
                """
                        {
                          "un_alphaField1_post" : "aValue1",
                          "un_alphaField2_post" : "aValue2",
                          "betaClasses" : [ {
                            "betaField1" : "bValue11",
                            "betaField2" : "bValue12"
                          }, {
                            "betaField1" : "bValue21",
                            "betaField2" : "bValue22"
                          } ]
                        }"""
                , s, true);
    }

    @AllArgsConstructor
    @Getter
    static class BaseClass {

        @JsonUnwrapped(prefix = "un_", suffix = "_post")
        EmbeddedClassAlpha classAlpha;

        Collection<EmbeddedClassBeta> betaClasses;
    }

    @AllArgsConstructor
    @Getter
    static class EmbeddedClassAlpha {
        String alphaField1;
        String alphaField2;
    }

    @AllArgsConstructor
    @Getter
    static class EmbeddedClassBeta {
        String betaField1;
        String betaField2;
    }

}
