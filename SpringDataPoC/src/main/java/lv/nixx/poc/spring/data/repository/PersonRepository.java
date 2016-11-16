package lv.nixx.poc.spring.data.repository;

import lv.nixx.poc.spring.data.domain.Person;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}
