package lv.nixx.poc.repository.auditable;

public interface AuditableAwareOperations<T extends Auditable> {
    T saveWithAuditable(T entity);
    Iterable<T> saveAllWithAuditable(Iterable<T> entities);
}