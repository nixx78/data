package lv.nixx.poc.spring.data.repository.main;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lv.nixx.poc.spring.data.domain.main.Adress;

@Repository
public interface AdressRepository extends CrudRepository<Adress, Long> {
}
