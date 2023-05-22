package lv.nixx.samples.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import lv.nixx.samples.json.domain.ClassWithFieldsAndValuesFromMap;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.math.BigDecimal;

class ClassWithFieldsAndValuesFromMapTest {

    private final ObjectMapperService objectMapperService = new ObjectMapperService();

    @Test
    void toJsonTest() throws JsonProcessingException, JSONException {

        String json = objectMapperService.writeValueAsString(new ClassWithFieldsAndValuesFromMap(100L, "Description")
                .put("Alpha", new BigDecimal("10.01"), "Success")
                .put("Beta", new BigDecimal("11.11"), "Fail")
                .put("Gama", new BigDecimal("12.12"), "Warn"));

        System.out.println(json);

        JSONAssert.assertEquals("{\"id\": 100, \"description\": \"Description\", \"Alpha\": {\"value\": 10.01, \"status\": \"Success\"}, \"Beta\": {\"value\": 11.11, \"status\": \"Fail\"}, \"Gama\": {\"value\": 12.12, \"status\": \"Warn\"}}",
                json, true);
    }

}
