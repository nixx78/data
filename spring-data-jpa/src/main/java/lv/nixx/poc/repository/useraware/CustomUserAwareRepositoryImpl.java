package lv.nixx.poc.repository.useraware;

import jakarta.persistence.EntityManager;
import lv.nixx.poc.service.UserLoginProvider;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CustomUserAwareRepositoryImpl<T extends UserAware> implements CustomUserAwareRepository<T> {

    private final EntityManager entityManager;
    private final UserLoginProvider userLoginProvider;

    public CustomUserAwareRepositoryImpl(EntityManager entityManager, UserLoginProvider userLoginProvider) {
        this.entityManager = entityManager;
        this.userLoginProvider = userLoginProvider;
    }

    @Override
    public T saveWithUser(T entity) {
        entity.setUser(userLoginProvider.getCurrentUser());
        return entityManager.merge(entity);
    }

    @Override
    public Iterable<T> saveAllWithUser(Iterable<T> entities) {
        for (T entity : entities) {
            saveWithUser(entity);
        }
        return entities;
    }

}
