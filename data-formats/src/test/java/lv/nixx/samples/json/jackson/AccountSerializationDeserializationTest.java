package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import lv.nixx.samples.json.ObjectMapperService;
import lv.nixx.samples.json.domain.Account;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountSerializationDeserializationTest {

    private ObjectMapperService objectMapper = new ObjectMapperService();

    @Test
    public void serializeDeserializeTest() throws JsonProcessingException {

        Account a = new Account(100L, "001-20000-200", "Current account");

        final String s = objectMapper.writeValueAsString(a);

        System.out.println(s);

        Account da = objectMapper.readValue(s, Account.class);

        assertEquals("Objects must be equals", a, da);
    }

}
