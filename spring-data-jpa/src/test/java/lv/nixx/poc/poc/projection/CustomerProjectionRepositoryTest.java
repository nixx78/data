package lv.nixx.poc.poc.projection;

import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.poc.BaseTest;
import lv.nixx.poc.repository.projection.CustomerProjection;
import lv.nixx.poc.repository.projection.CustomerProjectionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerProjectionRepositoryTest extends BaseTest {

    @Autowired
    CustomerProjectionRepository customerProjectionRepository;

    @Test
    void findAllUsingProjectionTest() {

        customerProjectionRepository.saveAll(List.of(
                new Customer()
                        .setName("Name1")
                        .setSurname("Surname1")
                        .setType("Type1"),
                new Customer()
                        .setName("Name2")
                        .setSurname("Surname2")
                        .setType("Type1")
        ));

        List<CustomerProjection> customersAsProjections = customerProjectionRepository.findAllCustomersAsProjection();

        CustomerProjection firstCustomer = customersAsProjections.get(0);

        assertAll(
                () -> assertEquals(2, customersAsProjections.size()),
                () -> assertAll(
                        () -> assertNotNull(firstCustomer.getId()),
                        () -> assertEquals("Name1 Surname1", firstCustomer.getNameSurname())
                )
        );
    }

}
