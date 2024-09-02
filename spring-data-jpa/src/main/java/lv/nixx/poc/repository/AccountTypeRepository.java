package lv.nixx.poc.repository;

import lv.nixx.poc.orm.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
}
