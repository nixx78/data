package lv.nixx.poc.service;

import jakarta.transaction.Transactional;
import lv.nixx.poc.orm.EntityWithVersion;
import lv.nixx.poc.repository.EntityWithVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EntityWithVersionUpdateService {

    private static final Logger log = LoggerFactory.getLogger(EntityWithVersionUpdateService.class);

    private final EntityWithVersionRepository repository;

    public EntityWithVersionUpdateService(EntityWithVersionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void updateEntityWithLock(Long id, String newName) {
        try {
            log.info("Try to lock table for id [{}]", id);

            EntityWithVersion entityForUpdate = repository.findByIdWithLock(id);

            log.info("Entity for update {}", entityForUpdate);

            TimeUnit.MILLISECONDS.sleep(2000);
            // Если установить задержку, то будет ошибка, поскольку запись заблокирована другим потоком:
            // org.h2.jdbc.JdbcSQLTimeoutException: Timeout trying to lock table "ENTITY_WITH_VERSION"; SQL statement:
            // select ewv1_0.id,ewv1_0.data,ewv1_0.version from ENTITY_WITH_VERSION ewv1_0 where ewv1_0.id=? for update [50200-224]
            entityForUpdate.setData(newName);
            EntityWithVersion saved = repository.save(entityForUpdate);

            log.info("Saved entity: {}", saved);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
