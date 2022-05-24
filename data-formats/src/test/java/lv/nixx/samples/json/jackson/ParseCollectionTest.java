package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lv.nixx.samples.json.ObjectMapperService;
import lv.nixx.samples.json.domain.Person;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParseCollectionTest {

    @Test
    void parseCollectionTest() throws JsonProcessingException {

        ObjectMapperService objectMapperService = new ObjectMapperService();

        Collection<Person> persons = List.of(
                new Person()
                        .setId(1)
                        .setName("Name1"),
                new Person()
                        .setId(2)
                        .setName("Name2")
        );

        String json = objectMapperService.writeValueAsString(persons);
        System.out.println(json);

        Collection<Person> parsedPersons = objectMapperService.readValue(json, new TypeReference<>() {
        });

        assertEquals(2, parsedPersons.size());
    }

}
