package lv.nixx.poc.spring.jdbc.repository;

import lv.nixx.poc.spring.jdbc.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

}
