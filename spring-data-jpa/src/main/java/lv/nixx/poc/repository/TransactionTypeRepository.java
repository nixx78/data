package lv.nixx.poc.repository;

import lv.nixx.poc.orm.TransactionType;
import lv.nixx.poc.repository.auditable.AuditableAwareOperations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long>, AuditableAwareOperations<TransactionType> {
}
