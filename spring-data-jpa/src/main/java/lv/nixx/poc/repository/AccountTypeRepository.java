package lv.nixx.poc.repository;

import lv.nixx.poc.orm.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
}
