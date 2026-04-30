package lv.nixx.poc.repository;

import lv.nixx.poc.orm.EntityForBatchSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface BatchDataRepository extends JpaRepository<EntityForBatchSave, Long> {
}
