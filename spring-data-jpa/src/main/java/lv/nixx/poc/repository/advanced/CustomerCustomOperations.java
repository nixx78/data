package lv.nixx.poc.repository.advanced;

import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.repository.useraware.CustomUserAwareRepository;

import java.util.Collection;

public interface CustomerCustomOperations extends CustomUserAwareRepository<Customer> {

    Collection<Customer> findCustomersUsingCustomCondition(String name, String type);

}
