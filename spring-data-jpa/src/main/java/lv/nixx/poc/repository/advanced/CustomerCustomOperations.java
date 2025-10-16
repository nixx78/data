package lv.nixx.poc.repository.advanced;

import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.repository.auditable.AuditableAwareOperations;

import java.util.Collection;

public interface CustomerCustomOperations extends AuditableAwareOperations<Customer> {

    Collection<Customer> findCustomersUsingCustomCondition(String name, String type);

    Collection<Customer> findCustomersByTypes(String... type);

}
