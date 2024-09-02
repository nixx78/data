package lv.nixx.poc.repository.advanced;

import lv.nixx.poc.orm.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCustomRepository extends JpaRepository<Customer, Long>, CustomerCustomOperations {
}
