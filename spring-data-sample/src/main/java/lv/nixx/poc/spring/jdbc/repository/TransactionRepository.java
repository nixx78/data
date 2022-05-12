package lv.nixx.poc.spring.jdbc.repository;

import lv.nixx.poc.spring.jdbc.Transaction;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Collection<Transaction> getByAccountId(String accountId);

    @Query("select * from TRANSACTION_TBL where CURRENCY=:currency ")
    Collection<Transaction> getByCurrency(String currency);

}
