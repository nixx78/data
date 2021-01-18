package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lv.nixx.samples.json.domain.action.CardAddAction;
import lv.nixx.samples.json.domain.action.GenericAction;
import lv.nixx.samples.json.domain.action.PersonRemoveAction;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class CustomDeserializerTest {

    private ObjectMapper objectMapper;

    public CustomDeserializerTest() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    public void cardActionSerializationDeserializationTest() throws Exception {
        CardAddAction a = new CardAddAction();
        a.setData("CARD.ACTION.DATA");

        String s = objectMapper.writeValueAsString(a);
        System.out.println(s);

        GenericAction genericAction = objectMapper.readValue(s, GenericAction.class);
        assertTrue(genericAction instanceof CardAddAction);
        System.out.println(genericAction);
    }

    @Test
    public void personActionSerializationDeserializationTest() throws Exception {
        PersonRemoveAction a = new PersonRemoveAction();
        a.setData("PERSON.ACTION.DATA");

        String s = objectMapper.writeValueAsString(a);
        System.out.println(s);

        GenericAction genericAction = objectMapper.readValue(s, GenericAction.class);
        assertTrue(genericAction instanceof PersonRemoveAction);
        System.out.println(genericAction);
    }

}
