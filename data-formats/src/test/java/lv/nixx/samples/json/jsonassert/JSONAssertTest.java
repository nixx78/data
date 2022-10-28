package lv.nixx.samples.json.jsonassert;

import com.fasterxml.jackson.core.JsonProcessingException;
import lv.nixx.samples.json.ObjectMapperService;
import lv.nixx.samples.json.domain.Account;
import lv.nixx.samples.json.domain.Transaction;
import lv.nixx.samples.json.domain.Txns;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static lv.nixx.samples.json.domain.Currency.EUR;
import static lv.nixx.samples.json.domain.Currency.USD;
import static lv.nixx.samples.json.domain.Type.CREDIT;
import static lv.nixx.samples.json.domain.Type.DEBIT;
import static org.junit.jupiter.api.Assertions.*;

class JSONAssertTest {

    String expectedJson = "[\"A\",\"B\",\"C\"]";
    String actualJson = "[\"B\",\"A\",\"C\"]";

    @Test
    void assertEquals1Test() {

        assertAll(
                () -> JSONAssert.assertEquals(expectedJson, actualJson, false),
                () -> JSONAssert.assertNotEquals(expectedJson, actualJson, true),
                () -> assertThrows(AssertionError.class, () -> JSONAssert.assertEquals(expectedJson, actualJson, true))
        );

    }

    @Test
    void assertEquals2Test() throws JSONException {

        JSONCompareResult jsonCompareResult = JSONCompare.compareJSON(expectedJson, actualJson, JSONCompareMode.STRICT);

        assertAll(
                () -> assertFalse(jsonCompareResult.passed()),
                () -> assertEquals("[0]\n" +
                        "Expected: A\n" +
                        "     got: B\n" +
                        " ; [1]\n" +
                        "Expected: B\n" +
                        "     got: A\n", jsonCompareResult.getMessage())
        );
    }

    @Test
    void simpleObjectParseSample() throws JSONException {

        JSONArray jsonArray = (JSONArray) JSONParser.parseJSON("[\"A\",\"B\",\"C\"]");

        for (int i = 0; i < jsonArray.length(); i++) {
            System.out.println(jsonArray.get(i));
        }

        List<String> jsonItems = IntStream.range(0, jsonArray.length())
                .mapToObj(index -> {
                    try {
                        return jsonArray.getString(index);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).toList();

        System.out.println(jsonItems);
    }

    @Test
    void complexObjectParseSample() throws JSONException, JsonProcessingException {

        String accountAsString = new ObjectMapperService().writeValueAsString(
                new Account(100L, "001-123456-1200", "Saving")
                        .setTxns(new Txns(Set.of(
                                new Transaction(1, 11, BigDecimal.valueOf(1.01), CREDIT, EUR),
                                new Transaction(2, 12, BigDecimal.valueOf(1.02), DEBIT, EUR),
                                new Transaction(3, 13, BigDecimal.valueOf(1.03), CREDIT, USD)
                        )))
        );

        JSONObject jsonObject = (JSONObject) JSONParser.parseJSON(accountAsString);

        assertAll(
                () -> assertEquals("Saving", jsonObject.getString("name")),
                () -> assertEquals(3, jsonObject.getJSONObject("txns").getJSONArray("transactions").length())
        );
    }


}
