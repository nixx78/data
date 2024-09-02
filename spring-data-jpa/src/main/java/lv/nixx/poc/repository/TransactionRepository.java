package lv.nixx.poc.repository;

import jakarta.transaction.Transactional;
import lv.nixx.poc.orm.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@Transactional
@RepositoryRestResource(exported = false)
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT t.* FROM TRANSACTION_TBL t JOIN (SELECT accountId, MAX(dtTimestamp) as maxTimestamp FROM " +
            "TRANSACTION_TBL t1 JOIN ACCOUNT_TBL a ON t1.accountId = a.id WHERE a.customerId = :customerId  GROUP BY t1.accountId) tj ON t.accountId = tj.accountId AND t.dtTimestamp = tj.maxTimestamp"
            , nativeQuery = true)
    Collection<Transaction> getLatestTransactionForEachCustomerAccount(@Param("customerId") Long customerId);


    @Modifying
    @Query(value = "update Transaction t set t.status = :newStatus where t.id = :id")
    void updateTransactionStatus(@Param("id") Long id, @Param("newStatus") String newStatus);

}
