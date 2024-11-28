package lv.nixx.poc.service.transaction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lv.nixx.poc.model.SaveResult;
import lv.nixx.poc.orm.Account;
import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.orm.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionUsingEntityManagerSandbox {

    private final EntityManagerFactory entityManagerFactory;

    public TransactionUsingEntityManagerSandbox(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public SaveResult createNewAccountUsingEntityManager(String accountName, Long customerId) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Customer customer = entityManager.find(Customer.class, customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer, id [%s] doesn't exists".formatted(customerId));
        }

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Account account = entityManager.merge(new Account(accountName, customer, null));

            if (accountName.contains("ERROR")) {
                throw new IllegalStateException("Internal error after Account save");
            }

            List<Transaction> savedTransaction = List.of(
                    new Transaction()
                            .setAccount(account)
                            .setStatus("OK")
                            .setAmount(BigDecimal.valueOf(10.00)),
                    new Transaction()
                            .setAccount(account)
                            .setStatus("OK")
                            .setAmount(BigDecimal.valueOf(10.01))
            );

            savedTransaction.forEach(t -> {
                entityManager.persist(t);
                entityManager.flush();
            });

            transaction.commit();

            return new SaveResult(account.getId(), account.getName(), savedTransaction.stream().map(Transaction::getId).toList());
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }

    }

}
