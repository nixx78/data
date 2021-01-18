package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lv.nixx.samples.json.domain.subclass.Child1;
import lv.nixx.samples.json.domain.subclass.Child2;
import lv.nixx.samples.json.domain.subclass.Parent;
import org.junit.Test;

import static lv.nixx.samples.json.domain.subclass.Type.*;
import static org.junit.Assert.assertEquals;

public class JsonSubtypeTest {

    private ObjectMapper mapper;


    public JsonSubtypeTest() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void jsonWithChildTest() throws Exception {

        Child1 child1 = new Child1("parent.value", "child1.value");
        Child2 child2 = new Child2("parent.value", "child2.value");

        String s1 = mapper.writeValueAsString(child1);
        System.out.println(s1);

        String s2 = mapper.writeValueAsString(child2);
        System.out.println(s2);

        Child1 child1FromJson = (Child1) mapper.readValue(s1, Parent.class);
        System.out.println(child1FromJson);

        assertEquals(TYPE_1, child1FromJson.getType());

        Child2 child2FromJson = (Child2) mapper.readValue(s2, Parent.class);
        System.out.println(child2FromJson);

        assertEquals(TYPE_2, child2FromJson.getType());

    }


}
