package lv.nixx.poc.repository.advanced;

import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.repository.useraware.UserAwareOperations;

import java.util.Collection;

public interface CustomerCustomOperations extends UserAwareOperations<Customer> {

    Collection<Customer> findCustomersUsingCustomCondition(String name, String type);

}
