package lv.nixx.poc.repository.advanced;

import lv.nixx.poc.orm.Customer;

import java.util.Collection;

//TODO реализовать общий механизм заполнения поля пользователя (common interface UserAware)
public interface CustomerCustomOperations {
    Collection<Customer> findCustomersUsingCustomCondition(String name, String type);
}
