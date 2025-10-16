package lv.nixx.poc.repository.auditable;

import jakarta.persistence.EntityManager;
import lv.nixx.poc.service.UserLoginProvider;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@Transactional
public class AuditableAwareOperationsImpl<T extends Auditable> implements AuditableAwareOperations<T> {

    protected final EntityManager entityManager;
    private final UserLoginProvider userLoginProvider;

    public AuditableAwareOperationsImpl(EntityManager entityManager, UserLoginProvider userLoginProvider) {
        this.entityManager = entityManager;
        this.userLoginProvider = userLoginProvider;
    }

    @Override
    public T saveWithAuditable(T entity) {
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(userLoginProvider.getCurrentUser());
        return entityManager.merge(entity);
    }

    @Override
    public Iterable<T> saveAllWithAuditable(Iterable<T> entities) {
        for (T entity : entities) {
            saveWithAuditable(entity);
        }
        return entities;
    }

}
