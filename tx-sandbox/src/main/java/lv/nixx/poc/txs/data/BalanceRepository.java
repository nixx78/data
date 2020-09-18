package lv.nixx.poc.txs.data;

import lv.nixx.poc.txs.data.model.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<AccountBalance, Long> {
}
