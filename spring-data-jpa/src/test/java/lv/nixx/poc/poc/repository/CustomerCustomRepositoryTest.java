package lv.nixx.poc.poc.repository;

import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.poc.BaseTest;
import lv.nixx.poc.repository.advanced.CustomerCustomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerCustomRepositoryTest extends BaseTest {

    @Autowired
    CustomerCustomRepository customerCustomRepository;

    @BeforeEach
    void cleanup() {
        customerCustomRepository.deleteAll();
    }

    @Test
    void saveWithUserTest() {
        Customer customer = customerCustomRepository.saveWithUser(new Customer().setName("Name.Value").setSurname("SurnameValue"));
        assertEquals("user.name", customer.getUser());
    }

    @Test
    void saveAllWithUserTest() {

        customerCustomRepository.saveAllWithUser(List.of(
                new Customer().setName("Name.Value1").setSurname("SurnameValue1"),
                new Customer().setName("Name.Value2").setSurname("SurnameValue2")
        ));

        assertThat(customerCustomRepository.findAll()
                .stream()
                .map(Customer::getUser)
                .toList()).isEqualTo(List.of("user.name", "user.name"));
    }


    @Test
    void findAllUsingSpecificationTest() {

        customerCustomRepository.saveAll(List.of(
                new Customer()
                        .setName("Name1")
                        .setType("Type1"),
                new Customer()
                        .setName("Name_xyz_1")
                        .setType("Type1"),
                new Customer()
                        .setName("xyz_1")
                        .setType("Type1"),
                new Customer()
                        .setName("Name_xyz_1")
                        .setType("Type2"),
                new Customer()
                        .setName("xyz_1")
                        .setType("Type2")
        ));

        Collection<Customer> result = customerCustomRepository.findCustomersUsingCustomCondition("xyz", "Type1");
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(List.of(
                        new Customer()
                                .setName("Name_xyz_1")
                                .setType("Type1"),
                        new Customer()
                                .setName("xyz_1")
                                .setType("Type1")
                ));

    }

}
