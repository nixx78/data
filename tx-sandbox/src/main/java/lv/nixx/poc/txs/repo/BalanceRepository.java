package lv.nixx.poc.txs.repo;

import lv.nixx.poc.txs.orm.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<AccountBalance, Long> {
}
