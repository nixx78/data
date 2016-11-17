package lv.nixx.poc.spring.data.repository;

import lv.nixx.poc.spring.data.domain.Adress;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends CrudRepository<Adress, Long> {
}
