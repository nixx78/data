package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lv.nixx.samples.json.domain.Person;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GenericDeserializer {

    @Test
    public void deserializeAndEnrich() throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();

        String s = om.writeValueAsString(
                List.of(
                        new Person()
                                .setId(100)
                                .setName("Name1")
                                .setSalary(BigDecimal.valueOf(1000.77)),
                        new Person()
                                .setId(200)
                                .setName("Name2")
                                .setSalary(BigDecimal.valueOf(2000.77))
                )
        );

        Collection<Map<Object, Object>> modifiedCollection = om.readValue(s, Collection.class);

        int t = 1;
        for (Map<Object, Object> m : modifiedCollection) {
            m.put("newKey", t);
            t++;
        }

        String s1 = om.writerWithDefaultPrettyPrinter().writeValueAsString(modifiedCollection);
        System.out.println(s1);

    }

}
