package lv.nixx.poc.spring.jdbc.repository;

import lv.nixx.poc.spring.jdbc.logevent.LogEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<LogEntry, Long> {
}
