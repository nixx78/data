package lv.nixx.poc.repository;

import lv.nixx.poc.orm.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<TransactionType, Long> {
}
