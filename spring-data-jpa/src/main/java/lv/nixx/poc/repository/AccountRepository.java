package lv.nixx.poc.repository;

import lv.nixx.poc.orm.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
