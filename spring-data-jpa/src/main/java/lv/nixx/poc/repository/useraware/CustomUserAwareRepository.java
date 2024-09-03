package lv.nixx.poc.repository.useraware;

public interface CustomUserAwareRepository<T extends UserAware> {
    T saveWithUser(T entity);
    Iterable<T> saveAllWithUser(Iterable<T> entities);
}