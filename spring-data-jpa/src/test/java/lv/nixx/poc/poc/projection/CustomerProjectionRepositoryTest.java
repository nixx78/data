package lv.nixx.poc.poc.projection;

import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.poc.BaseTest;
import lv.nixx.poc.repository.projection.CustomerProjection;
import lv.nixx.poc.repository.projection.CustomerProjectionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

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
                        .setType("Type2")
        ));

        Collection<CustomerProjection> customersAsProjections = customerProjectionRepository.findAllCustomersAsProjection()
                .stream()
                .sorted(Comparator.comparingLong(CustomerProjection::getId))
                .toList();

        assertThat(customersAsProjections)
                .extracting(CustomerProjection::getNameSurname, CustomerProjection::getType)
                .containsExactly(
                        tuple("Name1 Surname1", "Type1"),
                        tuple("Name2 Surname2", "Type2")
                );

    }

}
