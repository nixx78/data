package lv.nixx.samples.json.jsonassert;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import static org.junit.jupiter.api.Assertions.*;

class JSONAssertTest {

    String expectedJson = "[\"A\",\"B\",\"C\"]";
    String actualJson = "[\"B\",\"A\",\"C\"]";

    @Test
    void assertEqualsTest() {

        assertAll(
                () -> JSONAssert.assertEquals(expectedJson, actualJson, false),
                () -> JSONAssert.assertNotEquals(expectedJson, actualJson, true),
                () -> assertThrows(AssertionError.class, () -> JSONAssert.assertEquals(expectedJson, actualJson, true))
        );

    }

    @Test
    void assertEqualsTes1t() throws JSONException {

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


        System.out.println("-");
    }


}
