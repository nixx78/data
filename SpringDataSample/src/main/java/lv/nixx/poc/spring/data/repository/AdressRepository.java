package lv.nixx.poc.spring.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lv.nixx.poc.spring.domain.Adress;

@Repository
public interface AdressRepository extends CrudRepository<Adress, Long> {
}
