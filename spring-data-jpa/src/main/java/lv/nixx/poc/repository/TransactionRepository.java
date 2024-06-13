package lv.nixx.poc.repository;

import lv.nixx.poc.orm.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Collection;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT t.* FROM TRANSACTION_TBL t JOIN (SELECT accountId, MAX(dtTimestamp) as maxTimestamp FROM " +
            "TRANSACTION_TBL GROUP BY accountId) tj ON t.accountId = tj.accountId AND t.dtTimestamp = tj.maxTimestamp"
            , nativeQuery = true)
    Collection<Transaction> getLatestTransaction();

}
