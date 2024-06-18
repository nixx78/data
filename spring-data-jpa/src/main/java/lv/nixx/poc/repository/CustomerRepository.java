package lv.nixx.poc.repository;

import lv.nixx.poc.orm.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
