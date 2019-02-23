package lv.nixx.poc.spring.data.repository.main;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lv.nixx.poc.spring.data.domain.main.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}
