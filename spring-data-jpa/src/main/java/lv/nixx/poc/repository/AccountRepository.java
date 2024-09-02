package lv.nixx.poc.repository;

import lv.nixx.poc.orm.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AccountRepository extends JpaRepository<Account, Long> {
}
