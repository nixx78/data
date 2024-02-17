package lv.nixx.poc.springdatarest;


import lv.nixx.poc.springdatarest.orm.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}