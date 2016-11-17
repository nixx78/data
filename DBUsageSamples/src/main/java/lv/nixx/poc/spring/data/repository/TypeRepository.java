package lv.nixx.poc.spring.data.repository;

import lv.nixx.poc.spring.data.domain.CustomerType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends CrudRepository<CustomerType, String>{
}
