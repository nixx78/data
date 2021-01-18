package lv.nixx.samples.json.jackson;

import lv.nixx.samples.json.ObjectMapperService;
import lv.nixx.samples.json.domain.AdditionalProperties;
import lv.nixx.samples.json.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerConversionSample {

    private ObjectMapperService om = new ObjectMapperService();

    @Test
    public void toJsonFromJson() throws Exception {

        Customer c = new Customer(100L, "Name.value");

        final AdditionalProperties ap = c.getAdditionalProperties();

        ap.addProperty("p1", "v1");
        ap.addProperty("p2", "v2");

        final String s = om.writerWithDefaultPrettyPrinter().writeValueAsString(c);
        assertFalse(s.isEmpty());

        System.out.println("Customer as JSON");
        System.out.println(s);

        final Customer customer = om.readValue(s, Customer.class);
        assertEquals(Long.valueOf(100), customer.getId());
        assertEquals("Name.value", customer.getName());

        final AdditionalProperties parsedAddProps = customer.getAdditionalProperties();
        assertEquals("v1", parsedAddProps.getProperty("p1"));
        assertEquals("v2", parsedAddProps.getProperty("p2"));

        assertEquals(2, parsedAddProps.getAdditionalProperties().size());
    }

}
