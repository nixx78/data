package lv.nixx.poc.poc.repository;

import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.poc.BaseTest;
import lv.nixx.poc.repository.advanced.CustomerSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerSpecificationTest extends BaseTest {

    @Test
    void findAllUsingSpecificationTest() {

        customerRepository.saveAll(List.of(
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

        Specification<Customer> spec = Specification.where(null);

        // Можно также использовать это выражение, оно добавить условие 1=1
        // Specification<Customer> spec = (root, query, cb) -> cb.conjunction();

        spec = spec.and(CustomerSpecification.nameLike("xyz"))
                .and(CustomerSpecification.typeEquals("Type1"));

        assertThat((Collection<Customer>) customerRepository.findAll(spec)).usingRecursiveComparison()
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

    @Test
    void findByTypesUsingSpecificationTest() {

        customerRepository.saveAll(List.of(
                new Customer()
                        .setName("NameT1")
                        .setType("Type1"),
                new Customer()
                        .setName("NameT21")
                        .setType("Type2"),
                new Customer()
                        .setName("NameT22")
                        .setType("Type2"),
                new Customer()
                        .setName("Name_xyz_1")
                        .setType("Type3"),
                new Customer()
                        .setName("xyz_1")
                        .setType("Type4")
        ));

        List<String> names = customerRepository.findAll(typeIn("Type1", "Type2"))
                .stream()
                .map(Customer::getName)
                .toList();

        assertThat(names).isEqualTo(List.of("NameT1", "NameT21", "NameT22"));
    }

    private static Specification<Customer> typeIn(String... types) {
        return (root, query, cb) -> {
            if (types == null || types.length == 0) {
                return cb.conjunction();
            } else {
                return root.get("type").in(List.of(types));
            }
        };
    }

}
