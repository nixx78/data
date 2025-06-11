package lv.nixx.poc.repository.advanced;

import lv.nixx.poc.orm.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface CustomerCustomRepository extends JpaRepository<Customer, Long>, CustomerCustomOperations {

    @Query("SELECT c.name FROM Customer c WHERE c.type IN (:type)")
    Collection<String> findCustomerNamesByTypes(@Param("type") Collection<String> types);
}
