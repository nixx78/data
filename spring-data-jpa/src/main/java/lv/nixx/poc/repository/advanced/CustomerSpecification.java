package lv.nixx.poc.repository.advanced;

import lv.nixx.poc.orm.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    public static Specification<Customer> nameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Customer> typeEquals(String type) {
        return (root, query, builder) -> builder.equal(root.get("type"), type);
    }
}