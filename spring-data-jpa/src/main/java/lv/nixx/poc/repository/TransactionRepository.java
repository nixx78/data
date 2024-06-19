package lv.nixx.poc.repository;

import lv.nixx.poc.orm.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Collection;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT t.* FROM TRANSACTION_TBL t JOIN (SELECT accountId, MAX(dtTimestamp) as maxTimestamp FROM " +
            "TRANSACTION_TBL t1 JOIN ACCOUNT_TBL a ON t1.accountId = a.id WHERE a.customerId = :customerId  GROUP BY t1.accountId) tj ON t.accountId = tj.accountId AND t.dtTimestamp = tj.maxTimestamp"
            , nativeQuery = true)
    Collection<Transaction> getLatestTransactionForEachCustomerAccount(@Param("customerId") Long customerId);




}
