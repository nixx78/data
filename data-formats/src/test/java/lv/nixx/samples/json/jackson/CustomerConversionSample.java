package lv.nixx.samples.json.jackson;

import lv.nixx.samples.json.ObjectMapperService;
import lv.nixx.samples.json.domain.AdditionalProperties;
import lv.nixx.samples.json.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CustomerConversionSample {

    private final ObjectMapperService om = new ObjectMapperService();

    @Test
    public void toJsonFromJson() throws Exception {

        Customer c = new Customer(100L, "Name.value", null);

        c.addAdditionalProperty("p1", "v1");
        c.addAdditionalProperty("p2", "v2");

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
