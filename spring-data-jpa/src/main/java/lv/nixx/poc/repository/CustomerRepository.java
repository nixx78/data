package lv.nixx.poc.repository;

import lv.nixx.poc.orm.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    @Query("SELECT c FROM Customer c JOIN FETCH c.accounts  WHERE c.id=:id")
    Customer findCustomerWithAccounts(@Param("id") Long id);

    @EntityGraph(
            attributePaths = {"accounts", "accounts.type", "accounts.transactions", "accounts.transactions.type"}
    )
    @Query("SELECT c FROM Customer c")
    Collection<Customer> findCustomersWithAccounts();

    @EntityGraph(
            attributePaths = {"accounts", "accounts.type", "accounts.transactions", "accounts.transactions.type"}
    )
    @Query("SELECT c FROM Customer c WHERE c.id=:id")
    Customer findUsingEntityGraph(@Param("id") Long id);

    @Override
    List<Customer> findAll(Specification<Customer> spec);

}
