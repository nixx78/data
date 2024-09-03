package lv.nixx.poc.repository;

import lv.nixx.poc.orm.TransactionType;
import lv.nixx.poc.repository.useraware.CustomUserAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long>, CustomUserAwareRepository<TransactionType> {
}
