package lv.nixx.poc.poc.repository;

import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.poc.BaseTest;
import lv.nixx.poc.repository.advanced.CustomerCustomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerCustomRepositoryTest extends BaseTest {

    @Autowired
    CustomerCustomRepository customerCustomRepository;

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
