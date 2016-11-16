package lv.nixx.poc.spring.data.repository;

import lv.nixx.poc.spring.data.domain.CustomerExtension;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerExtensionRepository extends CrudRepository<CustomerExtension, Long> {
}
