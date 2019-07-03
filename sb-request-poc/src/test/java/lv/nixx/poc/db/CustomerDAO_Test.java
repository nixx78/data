package lv.nixx.poc.db;

import lv.nixx.poc.db.domain.Customer;
import lv.nixx.poc.db.domain.CustomerWithType;
import lv.nixx.poc.db.util.TestDataCreator;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class CustomerDAO_Test {

    private TestDataCreator testDataCreator = new TestDataCreator();
    private CustomerDAO customerDAO = new CustomerDAO();

    @Before
    public void init() {
        testDataCreator.clearData();
        testDataCreator.createTestData();
    }

    @Test
    public void findCustomersByFirstLastNameTest() {

        Collection<Customer> result = customerDAO.findCustomersByFirstLastName("Name1", "LastName1");
        assertEquals(1, result.size());

        System.out.println(result);
    }

    @Test
    public void customerRequestByFistLastNameTest() {

        Collection<CustomerWithType> result = customerDAO.findCustomersWithType("Name1", "LastName1", null);
        assertEquals(1, result.size());
    }

    @Test
    public void customerRequestByTypeTest() {

        Collection<CustomerWithType> result = customerDAO.findCustomersWithType(null, null, Arrays.asList("massMarket", "student"));
        assertEquals(5, result.size());
    }


}
