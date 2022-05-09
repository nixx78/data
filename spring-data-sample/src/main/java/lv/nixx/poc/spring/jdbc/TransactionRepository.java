package lv.nixx.poc.spring.jdbc;

import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionDTO, Long> {
}
