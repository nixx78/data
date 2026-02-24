package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lv.nixx.samples.json.domain.CustomDTO;
import lv.nixx.samples.json.domain.GenericFields;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

class GenericFieldsSample {


    private final ObjectMapper objectMapper;

    public GenericFieldsSample() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    void createAndParseTest() throws Exception {

        CustomDTO obj = new CustomDTO(777L, new GenericFields("gField1.Value", "gField2.Value"));

        String json = objectMapper.writeValueAsString(obj);

        JSONAssert.assertEquals("""
                       {
                        "id" :777,
                        "gField1" :"gField1.Value",
                        "gField2" :"gField2.Value"
                        }
                       """
                , json, true);
    }

}