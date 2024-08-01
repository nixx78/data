package lv.nixx.poc.repository;

import jakarta.persistence.LockModeType;
import lv.nixx.poc.orm.EntityWithVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityWithVersionRepository extends JpaRepository<EntityWithVersion, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM EntityWithVersion e WHERE e.id = :id")
    EntityWithVersion findByIdWithLock(@Param("id") Long id);

}
